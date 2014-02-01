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
import org.apache.log4j.Logger
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser
import org.apache.lucene.search.IndexSearcher

import javax.inject.Inject

/**
 * @author Abhijit Sarkar
 */
class MovieSearchService {
    private static final logger = Logger.getInstance(MovieSearchService.class)
    private static final DEFAULT_SEARCH_FIELD = 'title'

    @Inject
    IndexSearcher indexSearcher

    @Inject
    StandardQueryParser queryParser

    def search(queryString) {
        def query = queryParser.parse(queryString, DEFAULT_SEARCH_FIELD)
        def topDocs = indexSearcher.search(query, 10)
        def scoreDocs = topDocs.scoreDocs

        movieRips(scoreDocs)
    }

    private movieRips(scoreDocs) {
        def movieRips = []
        final hits = scoreDocs.length()

        for (i in 0..hits) {
            def movieRip = new MovieRip()
            def doc = indexSearcher.doc(hits[i].doc)

            movieRip.title = doc.get('title')
            movieRip.genres = doc.getValues('genres').toList()
            movieRip.releaseDate = Date.parse('MM/dd/yyyy', doc.get('releaseDate'))
            movieRip.director = new CastAndCrew(doc.get('director'))
            movieRip.stars = doc.getValues('stars').toList().collect {
                new CastAndCrew(it)
            }
            movieRip.imdbRating = doc.get('imdbRating')
        }

        movieRips
    }
}
