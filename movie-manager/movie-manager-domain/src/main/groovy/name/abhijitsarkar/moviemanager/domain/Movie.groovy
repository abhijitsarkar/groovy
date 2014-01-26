package name.abhijitsarkar.moviemanager.domain

class Movie {
	protected String title
	protected List<String> genres
	protected Date releaseDate
	protected CastAndCrew director
	protected List<CastAndCrew> stars
	protected float imdbRating
	protected URL imdbURL

	Movie() {
	}

	Movie(anotherMovie) {
		title = anotherMovie.title
		genres = anotherMovie.genres
		releaseDate = anotherMovie.releaseDate
		director = anotherMovie.director
		stars = anotherMovie.stars
		imdbRating = anotherMovie.imdbRating
		imdbURL = anotherMovie.imdbURL
	}

	@Override
	String toString() {
		"Movie[title:${title}, year:${releaseDate[Calendar.YEAR]}, genres:${genres}]"
	}

	@Override
	boolean equals(Object obj) {
		if (!this.class.isAssignableFrom(obj?.class)) {
			return false
		}

		def result = (this.title == obj.title)
		result &= ((this.releaseDate[Calendar.YEAR] ?: 0) == obj.releaseDate[Calendar.YEAR])
		result &= (this.genres?.size() == obj.genres?.size())
		result &= (this.genres?.containsAll(obj.genres as String[]))
	}

	@Override
	int hashCode() {
		def result = 17
		def c = 0
		def magicNum = 37

		c = title ? title.hashCode() : 0
		result = magicNum * result + c

		c = this.releaseDate[Calendar.YEAR] ?: 0
		result = magicNum * result + c

		// Use the Spread operator to sum all hash codes
		c = genres ? genres*.hashCode().sum() : 0
		result = magicNum * result + c
	}
}