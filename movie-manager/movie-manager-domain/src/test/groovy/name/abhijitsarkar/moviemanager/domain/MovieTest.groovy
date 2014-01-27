package name.abhijitsarkar.moviemanager.domain

import name.abhijitsarkar.moviemanager.mock.MovieMock

import org.junit.Before
import org.junit.Test

class MovieTest {
	def m

	@Before
	void setUp() {
		m = new MovieMock()
	}

	@Test
	void testNewMovie() {
		assert m instanceof Movie
		assert 'Terminator 2 Judgment Day' == m.title

		assert m.genres.find { it.toString() == 'Sci-Fi' } : 'Expected one of the genres to be Sci-Fi.'

		assert m.stars.find { it.name == 'Arnold Schwarzenegger' } :
		'Expected one of the stars to be Arnold Schwarzenegger.'

		assert m.releaseDate[Calendar.YEAR] == 1991
		assert m.imdbRating == 8.5f
	}

	@Test
	void testToString() {
		assert m.toString() == 'Movie[title:Terminator 2 Judgment Day, year:1991, genres:[Action, Sci-Fi, Thriller]]'
	}

	@Test
	void testCompareTo() {
		def o = new Movie()

		o.title = 'Terminator 2 Judgment Day'
		// Change the order from  'm'
		o.genres = [
			'Sci-Fi',
			'Action',
			'Thriller'
		]

		// Change the month and date from 'm'
		o.releaseDate = Date.parse('MM/dd/yyyy', '01/01/1991')

		assert o == m

		o.releaseDate = Date.parse('MM/dd/yyyy', '07/03/1992')

		assert o > m

		o.releaseDate = Date.parse('MM/dd/yyyy', '07/03/1991')

		o.genres.pop()

		assert o < m
	}

	@Test
	void testEquals() {
		def o = new Movie()

		o.title = 'Terminator 2 Judgment Day'
		// Change the order from  'm'
		o.genres = [
			'Sci-Fi',
			'Action',
			'Thriller'
		]

		// Change the month and date from 'm'
		o.releaseDate = Date.parse('MM/dd/yyyy', '01/01/1991')

		assert o == m

		o.releaseDate = Date.parse('MM/dd/yyyy', '07/03/1992')

		assert o != m

		o.releaseDate = Date.parse('MM/dd/yyyy', '07/03/1991')

		o.genres.pop()

		assert o != m
	}

	@Test
	void testHashCode() {
		def o = new Movie()

		o.title = 'Terminator 2 Judgment Day'
		// Change the order from  'm'
		o.genres = [
			'Sci-Fi',
			'Action',
			'Thriller'
		]

		// Change the month and date from 'm'
		o.releaseDate = Date.parse('MM/dd/yyyy', '01/01/1991')

		assert m.hashCode() == o.hashCode()

		o.releaseDate = Date.parse('MM/dd/yyyy', '07/03/1992')

		assert m.hashCode() != o.hashCode()

		o.releaseDate = Date.parse('MM/dd/yyyy', '07/03/1991')

		o.genres.pop()

		assert m.hashCode() != o.hashCode()
	}
}