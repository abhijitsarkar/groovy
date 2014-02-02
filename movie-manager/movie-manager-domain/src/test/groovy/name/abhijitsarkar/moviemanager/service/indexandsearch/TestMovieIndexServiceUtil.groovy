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

package name.abhijitsarkar.moviemanager.service.indexandsearch

import name.abhijitsarkar.moviemanager.annotation.MovieRips
import name.abhijitsarkar.moviemanager.domain.MovieRip
import name.abhijitsarkar.moviemanager.service.index.MovieIndexServiceUtil
import name.abhijitsarkar.moviemanager.service.index.analysis.NameAnalyzer
import name.abhijitsarkar.moviemanager.util.MovieMock
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexWriterConfig
import org.apache.lucene.store.Directory
import org.apache.lucene.store.RAMDirectory
import org.apache.lucene.util.Version

import javax.annotation.ManagedBean
import javax.enterprise.inject.Alternative
import javax.enterprise.inject.Disposes
import javax.enterprise.inject.Produces
import javax.enterprise.inject.Specializes

/**
 * @author Abhijit Sarkar
 */
@ManagedBean
@Alternative
@Specializes
class TestMovieIndexServiceUtil extends MovieIndexServiceUtil {
    private Directory indexDir

    @Produces
    @name.abhijitsarkar.moviemanager.annotation.SearchEngineVersion
    Version version() {
        Version.LUCENE_46
    }

    @Produces
    @name.abhijitsarkar.moviemanager.annotation.IndexDirectory
    Directory indexDirectory() {
        indexDir = new RAMDirectory()

        indexDir
    }

    @Produces
    @name.abhijitsarkar.moviemanager.annotation.IndexWriter
    IndexWriter openIndexWriter() {
        final IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_46,
                new NameAnalyzer(Version.LUCENE_46))
        new IndexWriter(indexDir, iwc)
    }

    @Produces
    @MovieRips
    Set<MovieRip> movieRips() {
        def movieRips = new HashSet<MovieRip>()
        movieRips << new MovieRip(new MovieMock())
    }

    void closeIndexWriter(
            @Disposes @name.abhijitsarkar.moviemanager.annotation.IndexWriter IndexWriter indexWriter) {
        indexWriter.close()
    }
}
