package name.abhijitsarkar.moviemanager.service.integrationtest

import mockit.Mocked
import mockit.NonStrictExpectations
import name.abhijitsarkar.moviemanager.service.MovieRipService

import org.junit.Before
import org.junit.Test

class MovieRipServiceTest {
	def service

	@Before
	void setUp() {
		service = new MovieRipService(genreList())
	}

	def genreList() {
		[
			'Action and Adventure',
			'Animation',
			'Comedy' ,
			'Documentary',
			'Drama',
			'Horror',
			'Romance',
			'R-Rated',
			'Mainstream Movies',
			'Sci-Fi',
			'Thriller',
			'X-Rated',
		]
	}

	@Test
	void testGetMovieRips() {
		def movieRips = service.getMovieRips('/Volumes/My Passport/My Movies/English')

		println movieRips.size()
	}
}
