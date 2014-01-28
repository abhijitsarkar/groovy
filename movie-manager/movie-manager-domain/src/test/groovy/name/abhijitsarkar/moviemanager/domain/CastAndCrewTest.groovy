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