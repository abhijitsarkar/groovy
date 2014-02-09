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

package name.abhijitsarkar.moviemanager.facade

import name.abhijitsarkar.moviemanager.domain.MovieRip
import name.abhijitsarkar.moviemanager.service.indexing.MovieIndexingService
import name.abhijitsarkar.moviemanager.service.rip.MovieRipService
import name.abhijitsarkar.moviemanager.service.search.MovieSearchService
import name.abhijitsarkar.moviemanager.service.search.QueryBuilder
import org.apache.lucene.search.Query

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

/**
 * @author Abhijit Sarkar
 */
@ApplicationScoped
class MovieFacade {
    @Inject
    MovieRipService movieRipService

    @Inject
    MovieIndexingService movieIndexingService

    @Inject
    MovieSearchService movieSearchService

    @Inject
    QueryBuilder queryBuilder

    void index(String movieDirectory) {
        Set<MovieRip> movieRips = movieRipService.getMovieRips(movieDirectory)

        movieIndexingService.index(movieRips)
    }

    Set<MovieRip> searchByField(String searchText, String indexField) {

        Query query = queryBuilder.perFieldQuery(searchText, indexField)

        movieSearchService.search(query)
    }

    Set<MovieRip> advancedSearch(String searchText) {

        Query query = queryBuilder.advancedQuery(searchText)

        movieSearchService.search(query)
    }

    Set<MovieRip> fetchAll() {

        Query query = queryBuilder.matchAllDocsQuery()

        movieSearchService.search(query)
    }
}
