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
import name.abhijitsarkar.moviemanager.domain.Movie
import name.abhijitsarkar.moviemanager.domain.MovieRip
import name.abhijitsarkar.moviemanager.service.index.IndexField
import org.apache.lucene.document.Document
import org.apache.lucene.search.Query
import org.apache.lucene.search.ScoreDoc
import org.apache.lucene.search.TopDocs
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
/**
 * @author Abhijit Sarkar
 */
@ApplicationScoped
class MovieSearchService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieSearchService)
    private static final int DEFAULT_NUM_RESULTS_TO_FETCH = 100

    @Inject
    MovieSearchServiceUtil movieSearchServiceUtil

    Set<MovieRip> search(Query query, int numResultsToFetch = DEFAULT_NUM_RESULTS_TO_FETCH) {

        TopDocs topDocs = movieSearchServiceUtil.indexSearcher.search(query, numResultsToFetch)

        movieRips(query, topDocs)
    }

    private Set<MovieRip> movieRips(Query query, TopDocs results) {
        final int totalHits = results.totalHits

        LOGGER.info("${totalHits} result(s) found for query: '${query.toString()}'.")

        ScoreDoc[] scoreDocs = results.scoreDocs
        final int hits = scoreDocs.length - 1

        Set<MovieRip> movieRips = [] as Set

        if (hits < 0) {
            return movieRips
        }

        for (i in 0..hits) {
            final Document doc = movieSearchServiceUtil.indexSearcher.doc(scoreDocs[i].doc)

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
