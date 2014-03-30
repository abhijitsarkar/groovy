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

package name.abhijitsarkar.moviedatabase.service.search

import name.abhijitsarkar.moviedatabase.test.integration.AbstractSpringIntegrationTest
import org.apache.lucene.store.RAMDirectory
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author Abhijit Sarkar
 */
class MovieSearchServiceIntegrationTest extends AbstractSpringIntegrationTest {
    @Autowired
    private MovieSearchService movieSearchService

    @Test
    void testNotNull() {
        assert movieSearchService?.indexDirectory
    }

    @Test
    void 'test that index directory is overridden correctly'() {
        assert movieSearchService?.indexDirectory instanceof RAMDirectory
    }
}
