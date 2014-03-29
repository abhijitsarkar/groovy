/*
 * Copyright (c) ${date}, the original author or authors.
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

class MovieRipTest {
    Movie m
    MovieRip mr

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
        assert mr.toString() ==
                'MovieRip[title:Terminator 2 Judgment Day, year:1991, genres:[Action, Sci-Fi, Thriller]]'
    }

    @Test
    void testEquals() {
        Movie o = new Movie()

        o.title = 'Terminator 2 Judgment Day'
        // Change the order from  'm'
        o.genres = [
                'Sci-Fi',
                'Action',
                'Thriller'
        ]

        // Change the month and date from 'm'
        o.releaseDate = Date.parse('MM/dd/yyyy', '01/01/1991')

        MovieRip om = new MovieRip(o)

        assert mr == om

        assert mr != 1
    }

    @Test
    void testHashCode() {
        Movie o = new Movie()

        o.title = 'Terminator 2 Judgment Day'
        // Change the order from  'm'
        o.genres = [
                'Sci-Fi',
                'Action',
                'Thriller'
        ]

        // Change the month and date from 'm'
        o.releaseDate = Date.parse('MM/dd/yyyy', '01/01/1991')

        MovieRip om = new MovieRip(o)

        assert mr.hashCode() == om.hashCode()
    }
}