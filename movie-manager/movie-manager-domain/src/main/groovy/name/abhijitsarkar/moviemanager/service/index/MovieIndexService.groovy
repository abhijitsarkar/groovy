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

package name.abhijitsarkar.moviemanager.service.index

import groovy.transform.PackageScope
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
class MovieIndexService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieIndexService)

    @Inject
    private MovieIndexServiceUtil movieIndexServiceUtil

    void index(Set<MovieRip> movieRips) {
        movieRips.each { movieRip ->
            delegate = this

            LOGGER.debug("Indexing movie rip ${movieRip}.")

            Document doc = new Document()

            addTextField(IndexField.TITLE, movieRip.title, true, doc)
            movieRip.genres.each { genre ->
                LOGGER.debug("Indexing movie genre ${genre}.")
                addTextField(IndexField.GENRES, genre, true, doc)
            }
            addDateField(IndexField.RELEASE_DATE, movieRip.releaseDate, true, doc)
            addTextField(IndexField.DIRECTOR, movieRip.director.name, true, doc)
            movieRip.stars.each { star ->
                LOGGER.debug("Indexing movie star ${star.name}.")
                addTextField(IndexField.STARS, star.name, true, doc)
            }
            addFloatField(IndexField.IMDB_RATING, movieRip.imdbRating, true, doc)
            addStringField(IndexField.IMDB_URL, movieRip.imdbURL, true, doc)
            addLongField(IndexField.FILE_SIZE, movieRip.fileSize, false, doc)
            addStringField(IndexField.FILE_EXTENSION, movieRip.fileExtension, false, doc)

            movieIndexServiceUtil.indexWriter.addDocument(doc)
        }

        movieIndexServiceUtil.indexWriter.commit()
    }

    @PackageScope
    void addDateField(IndexField indexField, Date fieldValue, boolean isStoredField, Document doc) {
        String releaseYear = DateTools.dateToString(fieldValue, DateTools.Resolution.YEAR)
        addStringField(indexField, releaseYear, isStoredField, doc)
    }

    @PackageScope
    void addStringField(IndexField indexField, String fieldValue, boolean isStoredField, Document doc) {
        Field field = new StringField(indexField.name(), trimToEmpty(fieldValue), fieldStorage(isStoredField))
        doc.add(field)
    }

    @PackageScope
    void addTextField(IndexField indexField, String fieldValue, boolean isStoredField, Document doc) {
        Field field = new TextField(indexField.name(), trimToEmpty(fieldValue), fieldStorage(isStoredField))
        doc.add(field)
    }

    @PackageScope
    void addFloatField(IndexField indexField, float fieldValue, boolean isStoredField, Document doc) {
        Field field = new FloatField(indexField.name(), fieldValue, fieldStorage(isStoredField))
        doc.add(field)
    }

    @PackageScope
    void addLongField(IndexField indexField, long fieldValue, boolean isStoredField, Document doc) {
        Field field = new LongField(indexField.name(), fieldValue, fieldStorage(isStoredField))
        doc.add(field)
    }

    private static Field.Store fieldStorage(boolean isStoredField) {
        isStoredField ? Field.Store.YES : Field.Store.NO
    }

    private static String trimToEmpty(String str) {
        str?.toString() ? str.trim() : ""
    }
}
