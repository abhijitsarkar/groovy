package name.abhijitsarkar.moviemanager.domain

import name.abhijitsarkar.moviemanager.mock.MovieMock

import org.junit.Before
import org.junit.Test

class MovieRipTest {
	def m
	def mr

	@Before
	void setUp() {
		m = new MovieMock()

		mr = new MovieRip(m)
	}

	@Test
	void testNewMovie() {
		assert mr instanceof MovieRip
		assert 'Terminator 2 Judgment Day' == mr.title

		assert mr.genres.find { it.toString() == 'Sci-Fi' } : 'Expected one of the genres to be Sci-Fi.'

		assert mr.stars.find { it.name == 'Arnold Schwarzenegger' } :
		'Expected one of the stars to be Arnold Schwarzenegger.'

		assert mr.releaseDate[Calendar.YEAR] == 1991
		assert mr.imdbRating == 8.5f
	}

	@Test
	void testToString() {
		assert mr.toString() == 'MovieRip[title:Terminator 2 Judgment Day, year:1991, genres:[Action, Sci-Fi, Thriller]]'
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

		def om = new MovieRip(o)

		assert mr == om

		assert mr != 1
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

		def om = new MovieRip(o)

		assert mr.hashCode() == om.hashCode()
	}
}