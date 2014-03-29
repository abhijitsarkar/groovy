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

package name.abhijitsarkar.moviedatabase.service.search

import org.apache.lucene.index.DirectoryReader
import org.apache.lucene.index.IndexReader
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.store.Directory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.annotation.PreDestroy

/**
 * @author Abhijit Sarkar
 */
@Component
class MovieSearchServiceHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieSearchServiceHelper)

    @Autowired
    Directory indexDirectory

    private IndexReader indexReader
    IndexSearcher indexSearcher

    protected IndexSearcher getIndexSearcher() {
        if (!indexSearcher) {
            LOGGER.info('Creating index reader for directory {}.', indexDirectory)

            indexReader = newIndexReader()
            indexSearcher = new IndexSearcher(indexReader)
        }

        indexSearcher
    }

    protected IndexReader newIndexReader() {
        DirectoryReader.open(indexDirectory)
    }

    @PreDestroy
    void preDestroy() {
        LOGGER.info('Closing index reader for directory {}.', indexDirectory)

        indexReader?.close()
    }
}
