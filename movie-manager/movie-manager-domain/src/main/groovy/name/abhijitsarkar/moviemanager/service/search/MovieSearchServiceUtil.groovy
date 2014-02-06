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

import name.abhijitsarkar.moviemanager.annotation.IndexDirectory
import name.abhijitsarkar.moviemanager.annotation.SearchEngineVersion
import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.core.SimpleAnalyzer
import org.apache.lucene.index.IndexReader
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.store.Directory
import org.apache.lucene.util.Version
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.context.Dependent
import javax.inject.Inject

/**
 * @author Abhijit Sarkar
 */
@Dependent
class MovieSearchServiceUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieSearchServiceUtil)

    @Inject
    @SearchEngineVersion
    private Version version

    @Inject
    @IndexDirectory
    private Directory indexDirectory

    private IndexReader indexReader
    private IndexSearcher indexSearcher
    private StandardQueryParser queryParser

    @PostConstruct
    void postConstruct() {
        indexReader = newIndexReader()
        indexSearcher = newIndexSearcher()
        queryParser = newQueryParser()
    }

    private IndexReader newIndexReader() {
        LOGGER.info("Creating index reader for directory ${indexDirectory}.")

        org.apache.lucene.index.DirectoryReader.open(indexDirectory)
    }

    private IndexSearcher newIndexSearcher() {
        LOGGER.debug('Creating index searcher.')

        indexSearcher = new IndexSearcher(indexReader)
    }

    private StandardQueryParser newQueryParser() {
        LOGGER.debug('Creating query parser.')

        queryParser = new StandardQueryParser(analyzer)
    }

    private Analyzer getAnalyzer() {
//        new NameAnalyzer(version)
        new SimpleAnalyzer(version)
    }

    @PreDestroy
    void preDestroy() {
        LOGGER.info("Closing index reader for directory ${indexDirectory}.")

        indexReader?.close()
    }
}
