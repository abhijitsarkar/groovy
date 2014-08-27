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

package name.abhijitsarkar.moviemanager.service.facade

import name.abhijitsarkar.moviemanager.annotation.IncludeFiles
import name.abhijitsarkar.moviemanager.annotation.IndexDirectory
import name.abhijitsarkar.moviemanager.annotation.MovieGenres
import name.abhijitsarkar.moviemanager.annotation.SearchEngineVersion
import name.abhijitsarkar.moviemanager.facade.MovieFacadeHelper
import org.apache.lucene.store.Directory
import org.apache.lucene.store.FSDirectory
import org.apache.lucene.util.Version

import javax.annotation.Priority
import javax.enterprise.inject.Alternative
import javax.enterprise.inject.Produces

import static javax.interceptor.Interceptor.Priority.APPLICATION

/**
 * @author Abhijit Sarkar
 */
@Alternative
@Priority(APPLICATION)
class TestMovieFacadeHelper extends MovieFacadeHelper {
    @Override
    @Produces
    @MovieGenres
    List<String> genreList() {
        super.genreList()
    }

    @Override
    @Produces
    @IncludeFiles
    List<String> includes() {
        super.includes()
    }

    @Override
    @Produces
    @IndexDirectory
    Directory indexDirectory() {
        // Override the actual indexing location so that tests don't destroy it
        File indexDirectory = new File('./build/lucene')
        FSDirectory.open(indexDirectory)
    }

    @Override
    @Produces
    @SearchEngineVersion
    Version searchEngineVersion() {
        super.searchEngineVersion()
    }
}
