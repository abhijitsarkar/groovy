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
		service = new MovieRipService(genreList(), includes())
	}

	def genreList() {
		[
			'Action and Adventure',
			'Animation',
			'Comedy' ,
			'Documentary',
			'Drama',
			'Horror',
			'R-Rated Mainstream Movies',
            'Romance',
			'Sci-Fi',
			'Thriller',
			'X-Rated'
		]
	}

    def includes() {
        [
            'avi',
            'mkv',
            'mp4',
            'divx',
            'mov'
        ]
    }

	@Test
	void testGetMovieRips() {
		def movieRips = service.getMovieRips('/Volumes/My Passport/My Movies/English')

		println movieRips.size()
	}
}
