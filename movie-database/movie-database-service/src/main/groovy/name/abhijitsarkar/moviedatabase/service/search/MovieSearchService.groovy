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

import name.abhijitsarkar.moviedatabase.domain.CastAndCrew
import name.abhijitsarkar.moviedatabase.domain.Movie
import name.abhijitsarkar.moviedatabase.domain.MovieRip
import name.abhijitsarkar.moviedatabase.service.index.IndexField
import org.apache.lucene.document.Document
import org.apache.lucene.search.Query
import org.apache.lucene.search.ScoreDoc
import org.apache.lucene.search.TopDocs
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author Abhijit Sarkar
 */
@Service
class MovieSearchService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieSearchService)
    private static final int DEFAULT_NUM_RESULTS_TO_FETCH = 100

    @Delegate
    @Autowired
    private MovieSearchServiceHelper helper

    Collection<MovieRip> search(Query query, int numResultsToFetch = DEFAULT_NUM_RESULTS_TO_FETCH) {

        TopDocs topDocs = indexSearcher.search(query, numResultsToFetch)

        movieRips(query, topDocs)
    }

    private Collection<MovieRip> movieRips(Query query, TopDocs results) {
        final int totalHits = results.totalHits

        LOGGER.info('{} result(s) found for query {}.', totalHits, query.toString())

        ScoreDoc[] scoreDocs = results.scoreDocs
        final int hits = scoreDocs.length - 1

        Collection<MovieRip> movieRips = [] as Set

        if (hits < 0) {
            return movieRips
        }

        for (i in 0..hits) {
            final Document doc = indexSearcher.doc(scoreDocs[i].doc)

            Movie movie = new Movie().with {
                title = doc.get(IndexField.TITLE.name())
                genres = doc.getValues(IndexField.GENRES.name()).toList()
                releaseDate = Date.parse('yyyy', doc.get(IndexField.RELEASE_DATE.name()))
                director = new CastAndCrew(doc.get(IndexField.DIRECTOR.name()))
                stars = doc.getValues('stars').toList().collect { aStar ->
                    new CastAndCrew(IndexField.STARS.name())
                }
                imdbRating = Float.parseFloat(doc.get(IndexField.IMDB_RATING.name()))

                it
            }

            movieRips << new MovieRip(movie)
        }

        movieRips
    }
}
