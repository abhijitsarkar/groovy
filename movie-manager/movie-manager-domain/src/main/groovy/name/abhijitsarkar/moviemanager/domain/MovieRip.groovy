package name.abhijitsarkar.moviemanager.domain

import java.util.regex.Pattern

class MovieRip extends Movie {
	long fileSize
	String fileExtension
	String parent

	MovieRip(movie) {
		super(movie)
	}

	@Override
	String toString() {
		def pattern = Pattern.compile('Movie*')
		super.toString().replaceAll(pattern, 'MovieRip')
	}

	@Override
	boolean equals(Object obj) {
		if (this.class != obj?.class) {
			return false
		}

		super.equals((Movie)obj)
	}

	@Override
	int hashCode() {
		// Codenarc is making me write extra code! On one hand, it thinks hashCode should've more code than just a
		// super call, on the other hand if I remove the method, Codenarc complains of it's absence.
		def hashCode = super.hashCode()

		hashCode
	}
}