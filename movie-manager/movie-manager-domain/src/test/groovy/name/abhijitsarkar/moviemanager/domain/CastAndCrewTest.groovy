package name.abhijitsarkar.moviemanager.domain

import org.junit.Before
import org.junit.Test

class CastAndCrewTest {
	def star1
	def star2

	@Before
	void setUp() {
		star1 = new CastAndCrew('Arnold Schwarzenegger')

		star2 = new CastAndCrew('Arnold Schwarzenegger')
	}

	@Test
	void testToString() {
		assert 'CastAndCrew[name:Arnold Schwarzenegger]' == star1?.toString()
	}

	@Test
	void testEquals() {
		assert star1 != null
		assert star1 == star2
		assert star1 != 1
		assert star1 != new CastAndCrew(null)
	}

	@Test
	void testHashCode() {
		assert star1.hashCode() == star2.hashCode()
		assert star1.hashCode() != new CastAndCrew(null).hashCode()
	}
}