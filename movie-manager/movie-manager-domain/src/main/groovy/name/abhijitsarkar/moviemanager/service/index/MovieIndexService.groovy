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
import name.abhijitsarkar.moviemanager.annotation.MovieRips
import name.abhijitsarkar.moviemanager.domain.MovieRip
import org.apache.lucene.document.DateTools
import org.apache.lucene.document.Document
import org.apache.lucene.document.Field
import org.apache.lucene.document.FloatField
import org.apache.lucene.document.LongField
import org.apache.lucene.document.StringField
import org.apache.lucene.document.TextField
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.store.Directory
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.enterprise.context.RequestScoped
import javax.enterprise.event.Event
import javax.inject.Inject

/**
 * @author Abhijit Sarkar
 */
@RequestScoped
class MovieIndexService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieIndexService)

    @name.abhijitsarkar.moviemanager.annotation.IndexWriter
    @Inject
    IndexWriter indexWriter

    @MovieRips
    @Inject
    Set<MovieRip> movieRips

    @Inject
    Event<Directory> indexingEvent

    void index() {
        movieRips.each { movieRip ->
            delegate = this

            LOGGER.debug("Indexing movie rip ${movieRip}.")

            Document doc = new Document()

            addStringField('title', movieRip.title, true, doc)
            movieRip.genres.each { genre ->
                LOGGER.debug("Indexing movie genre ${genre}.")
                addTextField('genres', genre, true, doc)
            }
            addDateField('releaseDate', movieRip.releaseDate, true, doc)
            addStringField('director', movieRip.director.name, true, doc)
            movieRip.stars.each { star ->
                LOGGER.debug("Indexing movie star ${star.name}.")
                addTextField('stars', star.name, true, doc)
            }
            addFloatField('imdbRating', movieRip.imdbRating, true, doc)
            addStringField('imdbURL', movieRip.imdbURL, true, doc)
            addLongField('fileSize', movieRip.fileSize, false, doc)
            addStringField('fileExtension', movieRip.fileExtension, false, doc)

            indexWriter.addDocument(doc)
        }

        Directory indexDirectory = indexWriter.directory

        indexWriter.commit()

        LOGGER.debug("Firing index event.")
        indexingEvent.fire(indexDirectory)
    }

    @PackageScope
    void addDateField(String fieldName, Date fieldValue, boolean isStoredField, Document doc) {
        addStringField(fieldName, DateTools.dateToString(fieldValue, DateTools.Resolution.YEAR), isStoredField, doc)
    }

    @PackageScope
    void addStringField(String fieldName, String fieldValue, boolean isStoredField, Document doc) {
        Field field = new StringField(fieldName, trimToEmpty(fieldValue), fieldStorage(isStoredField))
        doc.add(field)
    }

    @PackageScope
    void addTextField(String fieldName, String fieldValue, boolean isStoredField, Document doc) {
        Field field = new TextField(fieldName, trimToEmpty(fieldValue), fieldStorage(isStoredField))
        doc.add(field)
    }

    @PackageScope
    void addFloatField(String fieldName, float fieldValue, boolean isStoredField, Document doc) {
        Field field = new FloatField(fieldName, fieldValue, fieldStorage(isStoredField))
        doc.add(field)
    }

    @PackageScope
    void addLongField(String fieldName, long fieldValue, boolean isStoredField, Document doc) {
        Field field = new LongField(fieldName, fieldValue, fieldStorage(isStoredField))
        doc.add(field)
    }

    private static Field.Store fieldStorage(boolean isStoredField) {
        isStoredField ? Field.Store.YES : Field.Store.NO
    }

    private static String trimToEmpty(String str) {
        str?.toString() ? str.trim() : ""
    }
}
