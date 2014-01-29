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

package name.abhijitsarkar.moviemanager.service

import mockit.Mocked
import mockit.NonStrictExpectations
import name.abhijitsarkar.moviemanager.domain.MovieRip
import name.abhijitsarkar.moviemanager.mock.MovieMock
import org.apache.lucene.document.Document
import org.apache.lucene.index.IndexWriter
import org.junit.Test

/**
 * @author Abhijit Sarkar
 */
class MovieIndexServiceTest {
    private movieIndexService

    MovieIndexServiceTest() {
        movieIndexService = new MovieIndexService()
        movieIndexService.movieRips = [new MovieRip(new MovieMock())] as Set
    }

    @Test
    void testIndex(
            @Mocked MovieIndexUtil indexUtil, @Mocked Document doc, @Mocked IndexWriter indexWriter) {
        new NonStrictExpectations() {
            {
                indexUtil.openIndexWriter(); result = indexWriter

                new Document()

                indexWriter.addDocument(withInstanceLike(doc))

                indexUtil.closeIndexWriter(indexWriter)
            }
        }

        movieIndexService.index()
    }

}
