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

class MovieTest {
    Movie m

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
        Movie o = new Movie()

        o.title = 'Terminator 2 Judgment Day'
        // Change the order from  'm'
        o.genres = [
                'Sci-Fi',
                'Action',
                'Thriller'
        ] as Set

        // Change the month and date from 'm'
        o.releaseDate = Date.parse('MM/dd/yyyy', '01/01/1991')

        assert o == m

        o.releaseDate = Date.parse('MM/dd/yyyy', '07/03/1992')

        assert o > m

        o.releaseDate = Date.parse('MM/dd/yyyy', '07/03/1991')

        o.genres.remove('Thriller')

        assert o < m
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
        ] as Set

        // Change the month and date from 'm'
        o.releaseDate = Date.parse('MM/dd/yyyy', '01/01/1991')

        assert o == m

        o.releaseDate = Date.parse('MM/dd/yyyy', '07/03/1992')

        assert o != m

        o.releaseDate = Date.parse('MM/dd/yyyy', '07/03/1991')

        o.genres.remove('Thriller')

        assert o != m
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
        ] as Set

        // Change the month and date from 'm'
        o.releaseDate = Date.parse('MM/dd/yyyy', '01/01/1991')

        assert m.hashCode() == o.hashCode()

        o.releaseDate = Date.parse('MM/dd/yyyy', '07/03/1992')

        assert m.hashCode() != o.hashCode()

        o.releaseDate = Date.parse('MM/dd/yyyy', '07/03/1991')

        o.genres.remove('Thriller')

        assert m.hashCode() != o.hashCode()
    }
}