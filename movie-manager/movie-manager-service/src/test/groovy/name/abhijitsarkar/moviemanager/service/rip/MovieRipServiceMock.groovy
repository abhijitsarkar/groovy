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

import name.abhijitsarkar.moviemanager.domain.MovieMock
import name.abhijitsarkar.moviemanager.domain.MovieRip

import javax.annotation.Priority
import javax.enterprise.inject.Alternative
import javax.enterprise.inject.Specializes

import static javax.interceptor.Interceptor.Priority.APPLICATION

/**
 * @author Abhijit Sarkar
 */
@Alternative
@Priority(APPLICATION)
class MovieRipServiceMock extends MovieRipService {
    Set<MovieRip> getMovieRips(String movieDirectory) {
        // Do something with the parameter to make CodeNarc happy
        movieDirectory.toString()

        Set<MovieRip> movieRips = [] as SortedSet

        movieRips << new MovieRip(new MovieMock())
    }
}
