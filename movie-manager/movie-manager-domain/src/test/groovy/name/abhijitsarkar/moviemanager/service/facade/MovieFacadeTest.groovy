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

package name.abhijitsarkar.moviemanager.service.facade

import name.abhijitsarkar.moviemanager.domain.Movie
import name.abhijitsarkar.moviemanager.domain.MovieMock
import name.abhijitsarkar.moviemanager.domain.MovieRip
import name.abhijitsarkar.moviemanager.facade.MovieFacade
import name.abhijitsarkar.moviemanager.service.indexing.IndexField
import name.abhijitsarkar.moviemanager.util.AbstractCDITest
import org.junit.Before
import org.junit.Test

import javax.inject.Inject

/**
 * @author Abhijit Sarkar
 */
//@org.junit.experimental.categories.Category(CDISuiteTest)
class MovieFacadeTest extends AbstractCDITest {
    private final Movie mock = new MovieMock()

    @Inject
    MovieFacade movieFacade

    @Before
    void setUp() {
        movieFacade.index(null)
    }

    @Test
    void testSearchByTitle() {
        Set<MovieRip> movieRips = movieFacade.searchByField('terminator', IndexField.TITLE.name())
        verifySearchResult(movieRips)
    }

    @Test
    void testAdvancedSearchByTitle() {
        Set<MovieRip> movieRips = movieFacade.advancedSearch("${IndexField.TITLE.name()}:terminator")
        verifySearchResult(movieRips)
    }

    @Test
    void testSearchByReleaseDate() {
        Set<MovieRip> movieRips = movieFacade.searchByField('1991', IndexField.RELEASE_DATE.name())
        verifySearchResult(movieRips)
    }

    @Test
    void testAdvancedSearchByReleaseDate() {
        Set<MovieRip> movieRips = movieFacade.advancedSearch("${IndexField.RELEASE_DATE.name()}:[1991 TO 1991]")
        verifySearchResult(movieRips)
    }

    @Test
    void testSearchByStars() {
        Set<MovieRip> movieRips = movieFacade.searchByField('arnold', IndexField.STARS.name())
        verifySearchResult(movieRips)
    }

    @Test
    void testAdvancedSearchByStars() {
        Set<MovieRip> movieRips = movieFacade.advancedSearch("${IndexField.STARS.name()}:arnold")
        verifySearchResult(movieRips)
    }

    @Test
    void testSearchByDirector() {
        Set<MovieRip> movieRips = movieFacade.searchByField('cameron', IndexField.DIRECTOR.name())
        verifySearchResult(movieRips)
    }

    @Test
    void testAdvancedSearchByDirector() {
        Set<MovieRip> movieRips = movieFacade.advancedSearch("${IndexField.DIRECTOR.name()}:cameron")
        verifySearchResult(movieRips)
    }

    @Test
    void testFetchAll() {
        Set<MovieRip> movieRips = movieFacade.fetchAll()
        verifySearchResult(movieRips)
    }

    private void verifySearchResult(Set<MovieRip> movieRips) {
        assert movieRips.size() == 1
        assertIsMovieMock(movieRips.toList()[0])
    }

    private void assertIsMovieMock(MovieRip mr) {
        assert mr.title == mock.title
        assert mr.releaseDate[Calendar.YEAR] == mock.releaseDate[Calendar.YEAR]
        assert mr.genres == mock.genres
    }

//    @SuppressWarnings('unchecked')
//    private <T> T lookupBeanByType(final Class<T> clazz) {
//        final Iterator<Bean<?>> iter = beanManager.getBeans(clazz).iterator()
//        if (!iter.hasNext()) {
//            throw new IllegalStateException('CDI BeanManager cannot find an instance of type ${clazz.name}')
//        }
//        final Bean<T> bean = (Bean<T>) iter.next()
//        final CreationalContext<T> ctx = beanManager.createCreationalContext(bean)
//
//        (T) beanManager.getReference(bean, clazz, ctx)
//    }
}
