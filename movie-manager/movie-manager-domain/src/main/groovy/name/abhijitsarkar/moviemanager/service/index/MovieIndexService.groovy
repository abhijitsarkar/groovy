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
import org.apache.log4j.Logger
import org.apache.lucene.document.DateTools
import org.apache.lucene.document.Document
import org.apache.lucene.document.Field
import org.apache.lucene.document.FloatField
import org.apache.lucene.document.LongField
import org.apache.lucene.document.StringField
import org.apache.lucene.document.TextField
import org.apache.lucene.index.IndexWriter

import javax.annotation.ManagedBean
import javax.inject.Inject

/**
 * @author Abhijit Sarkar
 */
@ManagedBean
class MovieIndexService {
    private static final logger = Logger.getInstance(MovieIndexService.class)

    @name.abhijitsarkar.moviemanager.annotation.IndexWriter
    @Inject
    IndexWriter indexWriter

    @MovieRips
    @Inject
    Set<MovieRip> movieRips

    def index() {
        movieRips.each { movieRip ->
            delegate = this

            logger.debug("Indexing movie rip ${movieRip}...")

            def doc = new Document()

            addStringField('title', movieRip.title, Field.Store.YES, doc)
            movieRip.genres.each { genre ->
                logger.debug("Indexing movie genre ${genre}...")
                addTextField('genres', genre, Field.Store.YES, doc)
            }
            addDateField('releaseDate', movieRip.releaseDate, Field.Store.YES, doc)
            addStringField('director', movieRip.director, Field.Store.YES, doc)
            movieRip.stars.each { star ->
                logger.debug("Indexing movie star ${star.name}...")
                addTextField('stars', star.name, Field.Store.YES, doc)
            }
//            addTextField('stars', movieRip.stars.collect { it.name }.join(LIST_ELEMENT_SEPARATOR), Field.Store.YES, doc)
            addFloatField('imdbRating', movieRip.imdbRating, Field.Store.YES, doc)
            addStringField('imdbURL', movieRip.imdbURL, Field.Store.YES, doc)
            addLongField('fileSize', movieRip.fileSize, Field.Store.NO, doc)
            addStringField('fileExtension', movieRip.fileExtension, Field.Store.NO, doc)

            indexWriter.addDocument(doc)
        }
    }

    @PackageScope
    addDateField(fieldName, fieldValue, isStoredField, doc) {
        addStringField(fieldName, DateTools.dateToString(fieldValue, DateTools.Resolution.YEAR), isStoredField, doc)
    }

    @PackageScope
    addStringField(fieldName, fieldValue, isStoredField, doc) {
        def field = new StringField(fieldName, fieldValue, isStoredField ? Field.Store.YES : Field.Store.NO)
        doc.add(field)
    }

    @PackageScope
    addTextField(fieldName, fieldValue, isStoredField, doc) {
        def field = new TextField(fieldName, fieldValue, isStoredField ? Field.Store.YES : Field.Store.NO)
        doc.add(field)
    }

    @PackageScope
    addFloatField(fieldName, fieldValue, isStoredField, doc) {
        def field = new FloatField(fieldName, fieldValue, isStoredField ? Field.Store.YES : Field.Store.NO)
        doc.add(field)
    }

    @PackageScope
    addLongField(fieldName, fieldValue, isStoredField, doc) {
        def field = new LongField(fieldName, fieldValue, isStoredField ? Field.Store.YES : Field.Store.NO)
        doc.add(field)
    }
}
