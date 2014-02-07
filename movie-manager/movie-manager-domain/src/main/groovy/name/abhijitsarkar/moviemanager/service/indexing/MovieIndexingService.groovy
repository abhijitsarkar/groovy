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

package name.abhijitsarkar.moviemanager.service.indexing

import name.abhijitsarkar.moviemanager.domain.MovieRip
import org.apache.lucene.document.DateTools
import org.apache.lucene.document.Document
import org.apache.lucene.document.Field
import org.apache.lucene.document.FloatField
import org.apache.lucene.document.LongField
import org.apache.lucene.document.StringField
import org.apache.lucene.document.TextField
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.enterprise.context.RequestScoped
import javax.inject.Inject

/**
 * @author Abhijit Sarkar
 */
@RequestScoped
class MovieIndexingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieIndexingService)
    private static final String EMPTY = ''

    @Inject
    private MovieIndexingServiceHelper movieIndexServiceUtil

    private Field titleField
    private Field genresField
    private Field releaseDateField
    private Field directorField
    private Field starsField
    private Field imdbRatingField
    private Field imdbURLField
    private Field fileSizeField
    private Field fileExtensionField

    MovieIndexingService() {
        titleField = new TextField(IndexField.TITLE.name(), EMPTY, Field.Store.YES)

        releaseDateField = new StringField(IndexField.RELEASE_DATE.name(), EMPTY, Field.Store.YES)

        directorField = new TextField(IndexField.DIRECTOR.name(), EMPTY, Field.Store.YES)

        imdbRatingField = new FloatField(IndexField.IMDB_RATING.name(), 0.0f, Field.Store.YES)

        imdbURLField = new StringField(IndexField.IMDB_URL.name(), EMPTY, Field.Store.YES)

        fileSizeField = new LongField(IndexField.FILE_SIZE.name(), 0L, Field.Store.NO)

        fileExtensionField = new StringField(IndexField.IMDB_URL.name(), EMPTY, Field.Store.NO)
    }

    void index(Set<MovieRip> movieRips) {
        movieRips.each { movieRip ->
            delegate = this

            LOGGER.debug("Indexing movie rip ${movieRip}.")

            Document doc = new Document()

            addTextField(titleField, movieRip.title, doc)
            movieRip.genres.each { genre ->
                LOGGER.debug("Indexing movie genre ${genre}.")

                genresField = new TextField(IndexField.GENRES.name(), EMPTY, Field.Store.YES)

                addTextField(genresField, genre, doc)
            }
            addDateField(releaseDateField, movieRip.releaseDate, doc)
            addTextField(directorField, movieRip.director.name, doc)
            movieRip.stars.each { star ->
                LOGGER.debug("Indexing movie star ${star.name}.")

                starsField = new TextField(IndexField.STARS.name(), EMPTY, Field.Store.YES)

                addTextField(starsField, star.name, doc)
            }
            addFloatField(imdbRatingField, movieRip.imdbRating, doc)
            addTextField(imdbURLField, movieRip.imdbURL, doc)
            addLongField(fileSizeField, movieRip.fileSize, doc)
            addTextField(fileExtensionField, movieRip.fileExtension, doc)

            movieIndexServiceUtil.indexWriter.addDocument(doc)
        }

        movieIndexServiceUtil.indexWriter.commit()
    }

    private void addDateField(Field field, Date fieldValue, Document doc) {
        String releaseYear = DateTools.dateToString(fieldValue, DateTools.Resolution.YEAR)

        addTextField(field, releaseYear, doc)
    }

    private void addTextField(Field field, String fieldValue, Document doc) {
        field.setStringValue(trimToEmpty(fieldValue))
        doc.add(field)
    }

    private void addLongField(Field field, long fieldValue, Document doc) {
        field.setLongValue(fieldValue)
        doc.add(field)
    }

    private void addFloatField(Field field, float fieldValue, Document doc) {
        field.setFloatValue(fieldValue)
        doc.add(field)
    }

    private static String trimToEmpty(String str) {
        str?.toString() ? str.trim() : ''
    }
}
