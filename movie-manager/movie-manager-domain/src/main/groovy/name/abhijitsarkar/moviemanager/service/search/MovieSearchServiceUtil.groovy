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

package name.abhijitsarkar.moviemanager.service.search
import name.abhijitsarkar.moviemanager.annotation.SearchEngineVersion
import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.core.SimpleAnalyzer
import org.apache.lucene.index.DirectoryReader
import org.apache.lucene.index.IndexReader
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.store.Directory
import org.apache.lucene.util.Version
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.annotation.PreDestroy
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes
import javax.inject.Inject
/**
 * @author Abhijit Sarkar
 */
@ApplicationScoped
class MovieSearchServiceUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieSearchServiceUtil)

    @Inject
    @SearchEngineVersion
    private Version version

    private IndexReader indexReader
    private IndexSearcher indexSearcher
    private StandardQueryParser queryParser

    void init(@Observes Directory indexDirectory) {
        LOGGER.debug('Received index event.')

        LOGGER.info("Opening index reader for directory ${indexDirectory}")

        indexReader = DirectoryReader.open(indexDirectory)

        newIndexSearcher()

        newQueryParser()
    }

    private IndexSearcher newIndexSearcher() {
        LOGGER.debug('Creating new index searcher.')

        indexSearcher = new IndexSearcher(indexReader)
    }

    private StandardQueryParser newQueryParser() {
        LOGGER.debug('Creating new query parser.')

        queryParser = new StandardQueryParser(analyzer)
    }

    private Analyzer getAnalyzer() {
//        new NameAnalyzer(version)
        new SimpleAnalyzer(version)
    }

    IndexSearcher getIndexSearcher() {
        indexSearcher
    }

    StandardQueryParser getQueryParser() {
        queryParser
    }

    @PreDestroy
    void preDestroy() {
        LOGGER.info('Closing index reader.')

        indexReader?.close()
    }
}
