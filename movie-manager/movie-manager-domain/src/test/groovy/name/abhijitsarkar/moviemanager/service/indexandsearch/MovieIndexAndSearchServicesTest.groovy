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

package name.abhijitsarkar.moviemanager.service.indexandsearch

import name.abhijitsarkar.moviemanager.domain.Movie
import name.abhijitsarkar.moviemanager.domain.MovieRip
import name.abhijitsarkar.moviemanager.service.index.MovieIndexService
import name.abhijitsarkar.moviemanager.service.search.MovieSearchService
import name.abhijitsarkar.moviemanager.util.AbstractCDITest
import name.abhijitsarkar.moviemanager.util.MovieMock
import org.junit.Test

import javax.annotation.ManagedBean
import javax.annotation.PostConstruct
import javax.inject.Inject

/**
 * @author Abhijit Sarkar
 */
@ManagedBean
class MovieIndexAndSearchServicesTest extends AbstractCDITest {
    @Inject
    private MovieIndexService movieIndexService

    @Inject
    private MovieSearchService movieSearchService

    @PostConstruct
    private void postConstruct() {
        assert movieIndexService?.indexWriter
        assert movieIndexService?.movieRips

        assert movieSearchService?.indexSearcher
        assert movieSearchService?.queryParser
    }

    @Test
    void testSearch() {
        movieIndexService.index()

        Set<MovieRip> movieRips = movieSearchService.search('title: Terminator 2 Judgment Day')

        assert movieRips.size() == 1
        assert (movieRips[0] as Movie) == new MovieMock()
    }
}
