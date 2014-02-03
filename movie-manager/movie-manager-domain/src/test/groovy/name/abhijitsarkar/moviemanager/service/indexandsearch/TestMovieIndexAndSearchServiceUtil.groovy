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

import name.abhijitsarkar.moviemanager.annotation.IndexDirectory
import name.abhijitsarkar.moviemanager.annotation.MovieRips
import name.abhijitsarkar.moviemanager.annotation.SearchEngineVersion
import name.abhijitsarkar.moviemanager.domain.MovieRip
import name.abhijitsarkar.moviemanager.util.MovieMock
import org.apache.lucene.store.Directory
import org.apache.lucene.store.RAMDirectory
import org.apache.lucene.util.Version

import javax.enterprise.inject.Produces

/**
 * @author Abhijit Sarkar
 */
class TestMovieIndexAndSearchServiceUtil {

    @Produces
    @IndexDirectory
    Directory indexDirectory() {
        new RAMDirectory()
    }

    @Produces
    @SearchEngineVersion
    Version searchEngineVersion() {
        Version.LUCENE_46
    }

    @Produces
    @MovieRips
    Set<MovieRip> movieRips() {
        Set<MovieRip> movieRips = [] as SortedSet
        movieRips << new MovieRip(new MovieMock())
    }
}
