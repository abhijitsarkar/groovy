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


package name.abhijitsarkar.moviedatabase.test.integration.service.facade

import name.abhijitsarkar.moviedatabase.domain.MovieRip
import name.abhijitsarkar.moviedatabase.service.facade.MovieFacade
import name.abhijitsarkar.moviedatabase.service.index.IndexField
import name.abhijitsarkar.moviedatabase.test.integration.service.AbstractSpringIntegrationTest
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource

import javax.annotation.PostConstruct

/**
 * @author Abhijit Sarkar
 */
class MovieFacadeIntegrationTest extends AbstractSpringIntegrationTest {
    @Autowired
    private MovieFacade movieFacade

    private static final String MOVIE_DIR = new File(new ClassPathResource('movies').URL.toURI()).absolutePath

    private final Closure byTitleAndYear = { String title, int year, MovieRip mr ->
        title == mr.title && year == mr.releaseDate[Calendar.YEAR]
    }

    @PostConstruct
    void postConstruct() {
        movieFacade.index(MOVIE_DIR)
    }

    @Test
    void testNotNull() {
        assert movieFacade?.movieRipService
        assert movieFacade?.movieIndexService
        assert movieFacade?.movieSearchService
        assert movieFacade?.queryBuilder
    }

    @Test
    void testSearchByTitle() {
        Collection<MovieRip> searchResults = movieFacade.searchByField('exorcist', 'title')

        assert searchResults
        assert searchResults.size() == 1

        assert searchResults.find(byTitleAndYear.curry('The Exorcist', 1973))
    }

    @Test
    void testAdvancedSearchByReleaseDate() {
        String searchText = "${IndexField.RELEASE_DATE.name()}:[2001 TO 2001]"
        Collection<MovieRip> searchResults = movieFacade.advancedSearch(searchText)

        assert searchResults
        assert searchResults.size() == 1

        assert searchResults.find(byTitleAndYear.curry('A Beautiful Mind', 2001))
    }

    @Test
    void testFetchAll() {
        Collection<MovieRip> searchResults = movieFacade.fetchAll()

        assert searchResults
        assert searchResults.size() == 3

        assert searchResults.find(byTitleAndYear.curry('A Beautiful Mind', 2001))
        assert searchResults.find(byTitleAndYear.curry('The Exorcist', 1973))
        assert searchResults.find(byTitleAndYear.curry('Memento', 2000))
    }
}
