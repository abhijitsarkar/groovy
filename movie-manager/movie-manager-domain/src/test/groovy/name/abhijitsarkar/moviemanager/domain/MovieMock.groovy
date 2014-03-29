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

class MovieMock extends Movie {
    MovieMock() {
        title = 'Terminator 2 Judgment Day'
        genres = [
                'Action',
                'Sci-Fi',
                'Thriller'
        ]
        releaseDate = Date.parse('MM/dd/yyyy', '07/03/1991')
        director = new CastAndCrew('James Cameron')

        Set<CastAndCrew> stars = [] as Set
        stars.add(new CastAndCrew('Arnold Schwarzenegger'))
        stars.add(new CastAndCrew('Linda Hamilton'))
        stars.add(new CastAndCrew('Edward Furlong'))
        stars.add(new CastAndCrew('Robert Patrick'))

        this.stars = stars

        imdbRating = 8.5f
    }
}