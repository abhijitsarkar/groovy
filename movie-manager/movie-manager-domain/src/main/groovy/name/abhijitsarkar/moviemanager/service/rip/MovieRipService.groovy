/*
 * Copyright (c) 2014, the original author or authors.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * A copy of the GNU General Public License accompanies this software,
 * and is also available at http://www.gnu.org/licenses.
 */

/**
 * @author Abhijit Sarkar
 */

package name.abhijitsarkar.moviemanager.service.rip

import name.abhijitsarkar.moviemanager.annotation.IncludeFiles
import name.abhijitsarkar.moviemanager.annotation.MovieGenres
import name.abhijitsarkar.moviemanager.domain.Movie
import name.abhijitsarkar.moviemanager.domain.MovieRip
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.annotation.ManagedBean
import javax.annotation.PostConstruct
import javax.inject.Inject
import java.util.regex.Matcher
import java.util.regex.Pattern

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
    private static final PATTERN = Pattern.compile(MOVIE_NAME_WITH_RELEASE_YEAR_REGEX)
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieRipService)

    private final List<String> genres

    private final List<String> includes

    // For CDI to work, the injection point must be strongly typed
    @Inject
    MovieRipService(@MovieGenres List<String> genres, @IncludeFiles List<String> includes) {
        this.genres = genres
        this.includes = includes
    }

    @PostConstruct
    void postConstruct() {
        assert genres: 'Genre list must not be null.'
        assert includes: 'File includes must not be null.'
    }

    Set<MovieRip> getMovieRips(String movieDirectory) {
        File rootDir = new File(movieDirectory)

        if (!rootDir.isAbsolute()) {
            LOGGER.warn("Path ${movieDirectory} is not absolute: it's resolved to ${rootDir.absolutePath}")
        }

        if (!rootDir.exists() || !rootDir.isDirectory()) {
            throw new IllegalArgumentException("${rootDir.canonicalPath} does not exist or is not a directory.")
        }
        if (!rootDir.canRead()) {
            throw new IllegalArgumentException("${rootDir.canonicalPath} does not exist or is not readable.")
        }

        String currentGenre
        Set<MovieRip> movieRips = [] as SortedSet

        rootDir.eachFileRecurse { File f ->
            delegate = this

            if (f.isDirectory() && isGenre(f.name)) {
                currentGenre = f.name
            } else if (isMovieRip(f.name)) {
                MovieRip movieRip = parseMovieRip(f.name)

                movieRip.genres = currentGenre as List
                movieRip.fileSize = f.length()

                String parent = getParent(f, currentGenre, rootDir)

                if (!currentGenre?.equalsIgnoreCase(parent)) {
                    movieRip.parent = parent
                }

                LOGGER.info("Found movie: ${movieRip}")

                boolean isUnique = movieRips.add(movieRip)

                if (!isUnique) {
                    LOGGER.warn("Found duplicate movie: ${movieRip}")
                }
            }
        }
    }

    MovieRip parseMovieRip(String fileName) {
        String movieTitle
        final String movieRipFileExtension = getFileExtension(fileName)
        LOGGER.debug("movieRipFileExtension: ${movieRipFileExtension}")
        String lastPart

        int year = 0
        float imdbRating = -1.0f

        Matcher matcher = PATTERN.matcher(fileName)
        if (matcher.find() && matcher.groupCount() >= 1) {
            // 1st group is the title, always present
            final String group1 = matcher.group(1)
            LOGGER.debug("matcher.group(1): ${group1}")

            movieTitle = group1.trim()

            // If present, the 2nd group is the release year
            final String group2 = matcher.group(2)
            LOGGER.debug("matcher.group(2): ${group2}")
            year = group2 ? Integer.parseInt(group2) : year

            // If present, the 3rd group might be one of 2 things:
            // 1) The file extension
            // 2) A "qualifier" to the name like "part 1" and the file extension
            final String group3 = matcher.group(3)
            LOGGER.debug("matcher.group(3): ${group3}")
            lastPart = group3 ?: null

            if (lastPart && (lastPart != movieRipFileExtension)) {
                // Extract the qualifier
                String qualifier = lastPart[0..-(movieRipFileExtension.length() + 1)]
                LOGGER.debug("qualifier: ${qualifier}")
                movieTitle += qualifier
            }
        } else {
            LOGGER.debug("Found unconventional filename: ${fileName}")
            // Couldn't parse file name, extract as-is without file extension
            movieTitle = fileName[0..-(movieRipFileExtension.length() + 1)]
        }

        final Movie m = new Movie(title: movieTitle, imdbRating: imdbRating,
                releaseDate: Date.parse('MM/dd/yyyy', "01/01/${year}"))

        final MovieRip mr = new MovieRip(m)
        mr.fileExtension = "${movieRipFileExtension}"

        mr
    }

    boolean isMovieRip(String fileName) {
        includes.contains(getFileExtension(fileName).toLowerCase())
    }

    boolean isGenre(String fileName) {
        genres.contains(fileName)
    }

    String getFileExtension(fileName) {
        /* Unicode representation of char . */
        final String fullStop = '.'
        final int fullStopIndex = fileName.lastIndexOf(fullStop)

        if (fullStopIndex < 0) {
            return ''
        }

        fileName[fullStopIndex..-1]
    }

    final String getParent(File file, String currentGenre, File rootDirectory, String immediateParent = null) {
        File parentFile = file.parentFile

        if (!parentFile?.isDirectory() || parentFile?.compareTo(rootDirectory) <= 0) {
            return immediateParent
        }

        if (parentFile.name.equalsIgnoreCase(currentGenre)) {
            if (file.isDirectory()) {
                return file.name
            }
        }

        getParent(parentFile, currentGenre, rootDirectory, parentFile.name)
    }
}