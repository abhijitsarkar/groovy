package name.abhijitsarkar.moviemanager.service

import java.util.regex.Pattern

import javax.annotation.ManagedBean
import javax.annotation.PostConstruct
import javax.inject.Inject

import name.abhijitsarkar.moviemanager.annotation.GenreList
import name.abhijitsarkar.moviemanager.domain.Movie
import name.abhijitsarkar.moviemanager.domain.MovieRip

import org.apache.log4j.Logger

@ManagedBean
class MovieRipService {
	/*
	 * The following regex matches file names with release year in parentheses,
	 * something like Titanic (1997).mkv Each part of the regex is explained
	 * further:
	 * 
	 * ([-',!\\[\\]\\.\\w\\s]++) -> Matches one or more occurrences of any
	 * alphabet, number or the following special characters in the movie name:
	 * dash (-), apostrophe ('), comma (,), exclamation sign (!), square braces
	 * ([]), full stop (.)
	 * 
	 * (?:\\((\\d{4})\\)) -> Matches 4 digit release year within parentheses.
	 * 
	 * (.++) -> Matches one or more occurrences of any character.
	 */
	private static final MOVIE_NAME_WITH_RELEASE_YEAR_REGEX = "([-',!\\[\\]\\.\\w\\s]++)(?:\\((\\d{4})\\))?+(.++)"
	private static final pattern = Pattern.compile(MOVIE_NAME_WITH_RELEASE_YEAR_REGEX)
	private static logger = Logger.getInstance(MovieRipService.class)

	private genreList
	
	// For CDI to work, the injection point must be strongly typed
	@Inject
	MovieRipService(@GenreList List<String> genreList) {
		this.genreList = genreList
	}

	@PostConstruct
	void postConstruct() {
		assert genreList : 'Genre list must not be null.'
	}

	def getMovieRips(movieDirectory) throws IOException {
		def f = new File(movieDirectory)

		if (!f.isAbsolute()) {
			logger.warn("Path ${movieDirectory} is not absolute: it's resolved to ${movieDirectory.absolutePath}")
		}

		def movieRips = new TreeSet<MovieRip>()

		addToMovieRips(f, movieRips)
	}

	def addToMovieRips(rootDir, movieRips) {
		if (!rootDir.exists() || !rootDir.isDirectory()) {
			throw new IllegalArgumentException("${rootDir.canonicalPath} does not exist or is not a directory.")
		}
		if (!rootDir.canRead()) {
			throw new IllegalArgumentException("${rootDir.canonicalPath} does not exist or is not readable.")
		}

		def movieRip
		def parent
		def isUnique
		def currentGenre

		rootDir.eachFileRecurse { File f ->
			if (f.isDirectory()) {
				if (isGenre(f.name)) {
					currentGenre = f.name
				}
				addTomovieRips(f, movieRips)
			} else if (isMovieRip(f.name)) {
				movieRip = parseMovieRip(f.name)

				movieRip.genre = currentGenre as List
				movieRip.fileSize = f.length

				parent = this.getParent(f, currentGenre, null, rootDir)

				if (!parent?.equalsIgnoreCase(currentGenre.toString())) {
					movieRip.parent = parent
				}

				isUnique = movieRips.add(movieRip)

				if (!isUnique) {
					logger.warn("Found duplicate movie: ${movieRip}")
				}
			}
		}
	}

	def parseMovieRip(fileName) {
		def movieTitle
		def fullStop = '.'
		def movieRipFileExtension = getFileExtension(fileName)
		def lastPart

		def year = 0
		def imdbRating = -1.0f

		def matcher = pattern.matcher(fileName)
		if (matcher.find() && matcher.groupCount() >= 1) {
			// 1st group is the title, always present
			movieTitle = matcher.group(1).trim()

			// If present, the 2nd group is the release year
			year = matcher.group(2) ?: Integer.parseInt(matcher.group(2))

			// If present, the 3rd group might be one of 2 things:
			// 1) The file extension
			// 2) A "qualifier" to the name like "part 1" and the file extension
			lastPart = matcher.group(3) ?: null

			if (lastPart && (lastPart != movieRipFileExtension)) {
				// Extract the qualifier
				movieTitle += lastPart.substring(0, lastPart.length()
						- (movieRipFileExtension.length() + 1))
			}
		} else {
			logger.debug("Found unconventional filename: ${fileName}")
			// Couldn't parse file name, extract as-is without file extension
			movieTitle = fileName.substring(0, fileName.length()
					- (movieRipFileExtension.length() + 1))
		}

		def m = new Movie(title: movieTitle, imdbRating: imdbRating,
		releaseDate: Date.parse('MM/dd/yyyy', "01/01/${year}"))

		def mr = new MovieRip(m)
		mr.fileExtension = "${fullStop}${movieRipFileExtension}"

		mr
	}

	def isMovieRip(fileName) {
		try {
			MovieRipFileFormat.valueOf(MovieRipFileFormat.class,
					getFileExtension(fileName).toUpperCase())
		} catch (IllegalArgumentException iae) {
			return false
		}
		true
	}

	def isGenre(fileName) {
		genreList.contains(fileName)
	}

	def getFileExtension(fileName) {
		/* Unicode representation of char . */
		def fullStop = '.'
		def fullStopIndex = fileName.lastIndexOf(fullStop)

		if (fullStopIndex < 0) {
			return ''
		}

		fileName.substring(++fullStopIndex, fileName.length())
	}

	def getParent(file, currentGenre, immediateParent, rootDirectory) {
		def parentFile = file.parentFile

		if (!parentFile?.isDirectory() || parentFile?.compareTo(rootDirectory) <= 0) {
			return immediateParent
		}

		if (parentFile.name.equalsIgnoreCase(currentGenre.toString())) {
			if (file.isDirectory()) {
				return file.name
			}
		}

		getParent(parentFile, currentGenre, parentFile.name, rootDirectory)
	}

	private enum MovieRipFileFormat {
		AVI,  MKV,  MP4,  DIVX, MOV
	}
}