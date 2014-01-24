package name.abhijitsarkar.moviemanager.domain

import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test

class CastAndCrewTest {
	static star1
	static star2

	@BeforeClass
	static void setUp() {
		star1 = new CastAndCrew()
		star1.name = "Arnold Schwarzenegger"

		star2 = new CastAndCrew()
		star2.name = "Arnold Schwarzenegger"
	}

	@Test
	void testEquals() {
		Assert.assertEquals("Expected Arnold Schwarzenegger to be equal to himself.",
				star1, star2)
		Assert.assertFalse("Expected Arnold Schwarzenegger to be NOT equal to number 1.",
				star1.equals(1))
		Assert.assertEquals("Expected Arnold Schwarzenegger's name to be Arnold Schwarzenegger.",
				"Arnold Schwarzenegger", star1.toString())
	}
}