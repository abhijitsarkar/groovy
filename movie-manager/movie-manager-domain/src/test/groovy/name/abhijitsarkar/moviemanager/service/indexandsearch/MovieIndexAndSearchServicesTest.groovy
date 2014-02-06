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
import name.abhijitsarkar.moviemanager.service.index.IndexField
import name.abhijitsarkar.moviemanager.service.index.MovieIndexService
import name.abhijitsarkar.moviemanager.service.search.MovieSearchService
import name.abhijitsarkar.moviemanager.service.search.QueryBuilder
import name.abhijitsarkar.moviemanager.util.AbstractCDITest
import name.abhijitsarkar.moviemanager.util.MovieMock
import org.apache.lucene.search.Query
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

    @Inject
    private QueryBuilder queryBuilder

    private final Movie mock = new MovieMock()

    private boolean isAlreadyIndexed

    @PostConstruct
    void postConstruct() {
        assert movieIndexService
        assert movieSearchService
        assert queryBuilder
    }

    @Before
    void index() {
        if (!isAlreadyIndexed) {
            movieIndexService.index(movieRips())

            isAlreadyIndexed = true
        }
    }

    private Set<MovieRip> movieRips() {
        Set<MovieRip> movieRips = [] as SortedSet
        movieRips << new MovieRip(new MovieMock())
    }

    @Test
    void testSearchByTitle() {
        Query query = queryBuilder.buildQuery(IndexField.TITLE, 'terminator')
        fireSearch(query)
    }

    @Test
    void testSearchByReleaseDate() {
        Query query = queryBuilder.buildQuery(IndexField.RELEASE_DATE, '1991')
        fireSearch(query)
    }

    @Test
    void testSearchByStars() {
        Query query = queryBuilder.buildQuery(IndexField.STARS, 'arnold')
        fireSearch(query)
    }

    @Test
    void testSearchByDirector() {
        Query query = queryBuilder.buildQuery(IndexField.DIRECTOR, 'cameron')
        fireSearch(query)
    }

    private void fireSearch(Query query) {
        // Search is dependent on indexing. JUnit and CDI seem to be running independent threads
        // so in order for search to work, need to index from a JUnit method, not CDI lifycycle mehtod,
        // like PostConstruct
        Set<MovieRip> movieRips = movieSearchService.search(query)

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
