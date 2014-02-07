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
import name.abhijitsarkar.moviemanager.service.indexing.IndexField
import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.core.SimpleAnalyzer
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser
import org.apache.lucene.search.Query
import org.apache.lucene.util.Version
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.annotation.PostConstruct
import javax.enterprise.context.Dependent
import javax.inject.Inject
/**
 * @author Abhijit Sarkar
 */
@Dependent
class QueryBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryBuilder)
    private static final String DEFAULT_SEARCH_FIELD = IndexField.TITLE.name()

    @Inject
    @SearchEngineVersion
    private Version version

    private StandardQueryParser queryParser

    @PostConstruct
    void postConstruct() {
        queryParser = newQueryParser()
    }

    Query buildQuery(IndexField indexField, String searchText) {
        String queryString

        switch (indexField) {
            case IndexField.RELEASE_DATE:
                queryString = "${indexField.name()}:[${searchText} TO ${searchText}]".toString()
                break;
            default:
                queryString = "${indexField.name()}:${searchText}".toString()
                break;
        }

        queryParser.parse(queryString, DEFAULT_SEARCH_FIELD)
    }

    protected StandardQueryParser newQueryParser() {
        LOGGER.debug('Creating query parser.')

        new StandardQueryParser(analyzer)
    }

    protected Analyzer getAnalyzer() {
//        new NameAnalyzer(version)
        new SimpleAnalyzer(version)
    }
}
