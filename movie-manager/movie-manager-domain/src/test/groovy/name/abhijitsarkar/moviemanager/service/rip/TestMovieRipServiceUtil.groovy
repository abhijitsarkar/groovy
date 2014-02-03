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

package name.abhijitsarkar.moviemanager.service.rip

import name.abhijitsarkar.moviemanager.annotation.IncludeFiles
import name.abhijitsarkar.moviemanager.annotation.MovieGenres
import name.abhijitsarkar.moviemanager.domain.MovieRipFileExtension

import javax.enterprise.inject.Produces

/**
 * @author Abhijit Sarkar
 */
class TestMovieRipServiceUtil {
    @Produces
    @MovieGenres
    List<String> genreList() {
        [
                'Action and Adventure',
                'Animation',
                'Comedy',
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

    @Produces
    @IncludeFiles
    List<String> includes() {
        MovieRipFileExtension.values().collect {
            // GOTCHA ALERT: GString is not equal to String; "a" != 'a'
            ".${it.name().toLowerCase()}".toString()
        }
    }
}
