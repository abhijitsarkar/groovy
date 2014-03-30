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
import name.abhijitsarkar.moviedatabase.service.index.IndexField
import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.core.SimpleAnalyzer
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser
import org.apache.lucene.search.MatchAllDocsQuery
import org.apache.lucene.search.Query
import org.apache.lucene.util.Version
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct
import javax.validation.constraints.NotNull

import static org.springframework.util.Assert.notNull

/**
 * @author Abhijit Sarkar
 */
@Component
class QueryBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryBuilder)
    private static final String DEFAULT_SEARCH_FIELD = IndexField.TITLE.name()

    @Autowired
    Version version

    private StandardQueryParser queryParser

    @PostConstruct
    void postConstruct() {
        notNull(version, 'Lucene version must not be null.')

        queryParser = newQueryParser()
    }

    Query perFieldQuery(@NotNull String searchText, @NotNull String indexField) {
        LOGGER.debug('Per field query - field {}, search text {}.', indexField, searchText)

        IndexField idxField = IndexField.valueOf(IndexField, indexField.toUpperCase())
        String queryString

        switch (idxField) {
            case IndexField.RELEASE_DATE:
                queryString = "${idxField.name()}:[${searchText} TO ${searchText}]".toString()
                break
            default:
                queryString = "${idxField.name()}:${searchText}".toString()
                break
        }

        LOGGER.debug('Query - {}.', queryString)

        queryParser.parse(queryString, DEFAULT_SEARCH_FIELD)
    }

    Query advancedQuery(@NotNull String searchText) {
        LOGGER.debug('Advanced query - {}.', searchText)

        queryParser.parse(searchText, DEFAULT_SEARCH_FIELD)
    }

    Query matchAllDocsQuery() {
        LOGGER.debug('Match all docs query.')

        new MatchAllDocsQuery()
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
