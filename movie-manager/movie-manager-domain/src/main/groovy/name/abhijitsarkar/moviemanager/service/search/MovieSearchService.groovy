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

import name.abhijitsarkar.moviemanager.domain.CastAndCrew
import name.abhijitsarkar.moviemanager.domain.MovieRip
import org.apache.lucene.document.Document
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.search.Query
import org.apache.lucene.search.ScoreDoc
import org.apache.lucene.search.TopDocs
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.annotation.ManagedBean
import javax.inject.Inject

/**
 * @author Abhijit Sarkar
 */
@ManagedBean
class MovieSearchService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieSearchService)
    static final String DEFAULT_SEARCH_FIELD = 'title'
    static final int DEFAULT_NUM_RESULTS_TO_FETCH = 100

    @Inject
    @name.abhijitsarkar.moviemanager.annotation.IndexSearcher
    IndexSearcher indexSearcher

    @Inject
    @name.abhijitsarkar.moviemanager.annotation.QueryParser
    StandardQueryParser queryParser

    Set<MovieRip> search(String queryString, int numResultsToFetch = DEFAULT_NUM_RESULTS_TO_FETCH) {
        Query query = queryParser.parse(queryString, DEFAULT_SEARCH_FIELD)
        TopDocs topDocs = indexSearcher.search(query, numResultsToFetch)
        ScoreDoc[] scoreDocs = topDocs.scoreDocs

        movieRips(scoreDocs)
    }

    private Set<MovieRip> movieRips(ScoreDoc[] scoreDocs) {
        Set<MovieRip> movieRips = [] as Set
        final int hits = scoreDocs.length()

        for (i in 0..hits) {
            Document doc = indexSearcher.doc(hits[i].doc)

            MovieRip movieRip = new MovieRip().with {
                title = doc.get('title')
                genres = doc.getValues('genres').toList()
                releaseDate = Date.parse('MM/dd/yyyy', doc.get('releaseDate'))
                director = new CastAndCrew(doc.get('director'))
                stars = doc.getValues('stars').toList().collect { aStar ->
                    new CastAndCrew(aStar)
                }
                imdbRating = doc.get('imdbRating')

                it
            }

            movieRips << movieRip
        }

        movieRips
    }
}
