package name.abhijitsarkar.moviemanager.mock

import name.abhijitsarkar.moviemanager.domain.CastAndCrew
import name.abhijitsarkar.moviemanager.domain.Movie

class MovieMock extends Movie {
	MovieMock() {
		title = 'Terminator 2 Judgment Day'
		genres = [
			'Action',
			'Sci-Fi',
			'Thriller'
		]
		releaseDate = Date.parse('MM/dd/yyyy', '07/03/1991')
		director = new CastAndCrew('James Cameron')

		def stars = []
		stars.add(new CastAndCrew('Arnold Schwarzenegger'))
		stars.add(new CastAndCrew('Linda Hamilton'))
		stars.add(new CastAndCrew('Edward Furlong'))
		stars.add(new CastAndCrew('Robert Patrick'))

		this.stars = stars

		imdbRating = 8.5f
	}
}