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
        movieFacade.indexMovieRips(null)
    }

    @Test
    void testSearchByTitle() {
        Set<MovieRip> movieRips = movieFacade.searchMovieRips(IndexField.TITLE, 'terminator')
        verifySearchResult(movieRips)
    }

    @Test
    void testSearchByReleaseDate() {
        Set<MovieRip> movieRips = movieFacade.searchMovieRips(IndexField.RELEASE_DATE, '1991')
        verifySearchResult(movieRips)
    }

    @Test
    void testSearchByStars() {
        Set<MovieRip> movieRips = movieFacade.searchMovieRips(IndexField.STARS, 'arnold')
        verifySearchResult(movieRips)
    }

    @Test
    void testSearchByDirector() {
        Set<MovieRip> movieRips = movieFacade.searchMovieRips(IndexField.DIRECTOR, 'cameron')
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
