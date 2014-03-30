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

package name.abhijitsarkar.moviedatabase.service.index

import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.core.SimpleAnalyzer
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexWriterConfig
import org.apache.lucene.store.Directory
import org.apache.lucene.util.Version
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.annotation.PreDestroy

/**
 * @author Abhijit Sarkar
 */
@Component
class MovieIndexServiceHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieIndexServiceHelper)

    @Autowired
    Directory indexDirectory

    @Autowired
    Version version

    IndexWriter indexWriter

    protected IndexWriter getIndexWriter() {
        if (!indexWriter) {
            LOGGER.info('Creating index writer for directory {}.', indexDirectory)

            IndexWriterConfig iwc = new IndexWriterConfig(version, analyzer)
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE)

            indexWriter = new IndexWriter(indexDirectory, iwc)
        }

        indexWriter
    }

    protected Analyzer getAnalyzer() {
//        new NameAnalyzer(version)
        new SimpleAnalyzer(version)
    }

    @PreDestroy
    void preDestroy() {
        LOGGER.info('Closing index writer for directory {}.', indexDirectory)

        indexWriter?.close()
    }
}
