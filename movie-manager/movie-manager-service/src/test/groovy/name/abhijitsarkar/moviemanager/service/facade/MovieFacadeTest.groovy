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

package name.abhijitsarkar.moviemanager.service.facade

import name.abhijitsarkar.moviemanager.domain.Movie
import name.abhijitsarkar.moviemanager.domain.MovieMock
import name.abhijitsarkar.moviemanager.domain.MovieRip
import name.abhijitsarkar.moviemanager.facade.MovieFacade
import name.abhijitsarkar.moviemanager.service.index.IndexField
import name.abhijitsarkar.moviemanager.service.index.MovieIndexService
import name.abhijitsarkar.moviemanager.service.rip.MovieRipService
import name.abhijitsarkar.moviemanager.service.search.MovieSearchService
import name.abhijitsarkar.moviemanager.service.search.QueryBuilder
import org.apache.lucene.search.Query
import org.gmock.WithGMock
import org.junit.Before
import org.junit.Test

/**
 * @author Abhijit Sarkar
 */
@WithGMock
class MovieFacadeTest {
    private final Movie mock = new MovieMock()

    private MovieFacade movieFacade

    private MovieRipService movieRipService

    private MovieIndexService movieIndexService

    private MovieSearchService movieSearchService

    private QueryBuilder queryBuilder

    private Query query

    private movieRips

    @Before
    void setUp() {
        movieRipService = mock(MovieRipService)
        movieIndexService = mock(MovieIndexService)
        movieSearchService = mock(MovieSearchService)
        queryBuilder = mock(QueryBuilder)

        query = mock(Query)
        movieRips = makeMovieRips()

        movieFacade = new MovieFacade(movieIndexService: movieIndexService, movieRipService: movieRipService,
                movieSearchService: movieSearchService, queryBuilder: queryBuilder)
    }

    private Set<MovieRip> makeMovieRips() {
        Movie m = new MovieMock()
        [new MovieRip(m)] as Set
    }

    @Test
    void testSearchByTitle() {
        String searchText = 'terminator'
        String indexField = IndexField.TITLE.name()

        helpTestPerFieldQuery(searchText, indexField)
    }

    void helpTestPerFieldQuery(String searchText, String indexField) {
        setUpPerFieldQueryExpectations(searchText, indexField)

        verifyPerFieldQueryExpectations(searchText, indexField)
    }

    private setUpPerFieldQueryExpectations(String searchText, String indexField) {
        queryBuilder.perFieldQuery(searchText, indexField).returns(query)
        movieSearchService.search(query).returns(movieRips)
    }

    private verifyPerFieldQueryExpectations(String searchText, String indexField) {
        play {
            Set<MovieRip> mr = movieFacade.searchByField(searchText, indexField)

            assert mr == movieRips
        }
    }

    @Test
    void testSearchByReleaseDate() {
        String searchText = '1991'
        String indexField = IndexField.RELEASE_DATE.name()

        helpTestPerFieldQuery(searchText, indexField)
    }

    @Test
    void testSearchByStars() {
        String searchText = 'arnold'
        String indexField = IndexField.STARS.name()

        helpTestPerFieldQuery(searchText, indexField)
    }

    @Test
    void testSearchByDirector() {
        String searchText = 'cameron'
        String indexField = IndexField.DIRECTOR.name()

        helpTestPerFieldQuery(searchText, indexField)
    }

    @Test
    void testAdvancedSearchByTitle() {
        String searchText = "${IndexField.TITLE.name()}:terminator"

        helpTestAdvancedQuery(searchText)
    }

    void helpTestAdvancedQuery(String searchText) {
        setUpAdvancedQueryExpectations(searchText)

        verifyAdvancedQueryExpectations(searchText)
    }

    private setUpAdvancedQueryExpectations(String searchText) {
        queryBuilder.advancedQuery(searchText).returns(query)
        movieSearchService.search(query).returns(movieRips)
    }

    private verifyAdvancedQueryExpectations(String searchText) {
        play {
            Set<MovieRip> mr = movieFacade.advancedSearch(searchText)

            assert mr == movieRips
        }
    }

    @Test
    void testAdvancedSearchByReleaseDate() {
        String searchText = "${IndexField.RELEASE_DATE.name()}:[1991 TO 1991]"

        helpTestAdvancedQuery(searchText)
    }

    @Test
    void testAdvancedSearchByStars() {
        String searchText = "${IndexField.STARS.name()}:arnold"

        helpTestAdvancedQuery(searchText)
    }

    @Test
    void testAdvancedSearchByDirector() {
        String searchText = "${IndexField.DIRECTOR.name()}:cameron"

        helpTestAdvancedQuery(searchText)
    }

    @Test
    void testFetchAll() {
        queryBuilder.matchAllDocsQuery().returns(query)
        movieSearchService.search(query).returns(movieRips)

        play {
            Set<MovieRip> mr = movieFacade.fetchAll()

            assert mr == movieRips
        }
    }
}
