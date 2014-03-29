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

import name.abhijitsarkar.moviemanager.AbstractSpringIntegrationTest
import name.abhijitsarkar.moviemanager.domain.MovieRip
import name.abhijitsarkar.moviemanager.facade.MovieFacade
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author Abhijit Sarkar
 */
class MovieFacadeIntegrationTest extends AbstractSpringIntegrationTest {
    @Autowired
    private MovieFacade movieFacade

    @Test
    void testNotNull() {
        assert movieFacade?.movieRipService
        assert movieFacade?.movieIndexService
        assert movieFacade?.movieSearchService
        assert movieFacade?.queryBuilder
    }

    @Test
    void 'test end to end - rip, index, search'() {
        final String movieDir = new File(getClass().getResource('/movies').toURI()).absolutePath
        println movieDir

        assert movieFacade.index(movieDir)
        Set<MovieRip> searchResults = movieFacade.searchByField('exorcist', 'title')

        assert searchResults
        assert searchResults[0].title == 'The Exorcist'
        assert searchResults[0].releaseDate[Calendar.YEAR] == 1973
    }
}
