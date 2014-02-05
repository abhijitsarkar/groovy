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
import org.junit.Before
import org.junit.Test

import javax.annotation.PostConstruct
import javax.inject.Inject

/**
 * @author Abhijit Sarkar
 */
//@org.junit.experimental.categories.Category(CDISuiteTest)
class MovieIndexAndSearchServicesTest extends AbstractCDITest {
    @Inject
    private MovieIndexService movieIndexService

    @Inject
    private MovieSearchService movieSearchService

    private final Movie mock = new MovieMock()

    @PostConstruct
    void postConstruct() {
        assert movieIndexService
        assert movieSearchService
    }

    @Before
    void index() {
        movieIndexService.index()
    }

    @Test
    void testSearchByTitle() {
        // Search is dependent on indexing. JUnit and CDI seem to be running independent threads
        // so in order for search to work, need to index from a JUnit method, not CDI lifycycle mehtod,
        // like PostConstruct
        Set<MovieRip> movieRips = movieSearchService.search('title:terminator')

        assert movieRips.size() == 1
        assertIsMovieMock(movieRips.toList()[0])
    }

    @Test
    void testSearchByReleaseDate() {
        // Search is dependent on indexing. JUnit and CDI seem to be running independent threads
        // so in order for search to work, need to index from a JUnit method, not CDI lifycycle mehtod,
        // like PostConstruct
        Set<MovieRip> movieRips = movieSearchService.search('releaseDate:1991')

        assert movieRips.size() == 1
        assertIsMovieMock(movieRips.toList()[0])
    }

    @Test
    void testSearchByStars() {
        // Search is dependent on indexing. JUnit and CDI seem to be running independent threads
        // so in order for search to work, need to index from a JUnit method, not CDI lifycycle mehtod,
        // like PostConstruct
        Set<MovieRip> movieRips = movieSearchService.search('stars:arnold')

        assert movieRips.size() == 1
        assertIsMovieMock(movieRips.toList()[0])
    }

    @Test
    void testSearchByDirector() {
        // Search is dependent on indexing. JUnit and CDI seem to be running independent threads
        // so in order for search to work, need to index from a JUnit method, not CDI lifycycle mehtod,
        // like PostConstruct
        Set<MovieRip> movieRips = movieSearchService.search('director:cameron')

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
