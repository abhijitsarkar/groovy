package name.abhijitsarkar.moviemanager.domain

import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test

class MovieTest {
	static m

	@BeforeClass
	static void setUp() {
		m = new Movie()

		m.name = "Terminator 2 Judgment Day"
		m.genres = [
			"Action",
			"Sci-Fi",
			"Thriller"
		]
		m.releaseDate = Date.parse("MM/dd/yyyy", "07/03/1991")
		m.director = new CastAndCrew("James Cameron")

		def stars = []
		stars.add(new CastAndCrew("Arnold Schwarzenegger"))
		stars.add(new CastAndCrew("Linda Hamilton"))
		stars.add(new CastAndCrew("Edward Furlong"))
		stars.add(new CastAndCrew("Robert Patrick"))

		m.stars = stars

		m.imdbRating = 8.5f
	}

	@Test
	void testNewMovie() {
		Assert.assertTrue("Expected instance of Movie, got " + m?.class.name,
				m instanceof Movie)
		Assert.assertEquals("Wrong movie name.", "Terminator 2 Judgment Day", m.name)

		def genre = m.genres.grep { it.toString() == "Sci-Fi" }
		Assert.assertEquals("Expected one of the genres to be Sci-Fi.",
				1, genre.size())

		def star = m.stars.grep { it.toString() == "Arnold Schwarzenegger" }
		Assert.assertEquals("Expected one of the stars to be Arnold Schwarzenegger.",
				1, star.size())

		Assert.assertEquals("Expected release year to be 1991", 1991,
				m.releaseDate.getAt(Calendar.YEAR))
		Assert.assertEquals("Expected IMDB rating to be 8.5f", 8.5f, m.imdbRating, 0.1f)
	}
}