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
import name.abhijitsarkar.moviemanager.annotation.SearchEngineVersion
import name.abhijitsarkar.moviemanager.service.index.analysis.NameAnalyzer
import name.abhijitsarkar.moviemanager.service.search.MovieSearchServiceUtil
import org.apache.lucene.index.DirectoryReader
import org.apache.lucene.index.IndexReader
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.store.Directory
import org.apache.lucene.util.Version

import javax.annotation.ManagedBean
import javax.enterprise.inject.Alternative
import javax.enterprise.inject.Disposes
import javax.enterprise.inject.Produces
import javax.enterprise.inject.Specializes
import javax.inject.Inject

/**
 * @author Abhijit Sarkar
 */
@ManagedBean
@Alternative
@Specializes
class TestMovieSearchServiceUtil extends MovieSearchServiceUtil {

    @Inject
    @IndexDirectory
    private Directory indexDir

    @Inject
    @SearchEngineVersion
    private Version version

    @Produces
    @name.abhijitsarkar.moviemanager.annotation.IndexSearcher
    IndexSearcher newIndexSearcher() {
        IndexReader reader = DirectoryReader.open(indexDir)
        new IndexSearcher(reader)
    }

    @Produces
    @name.abhijitsarkar.moviemanager.annotation.QueryParser
    StandardQueryParser newQueryParser() {
        new StandardQueryParser(new NameAnalyzer(version))
    }

    void closeIndexReader(
            @Disposes @name.abhijitsarkar.moviemanager.annotation.IndexSearcher IndexSearcher indexSearcher) {
        indexSearcher.indexReader.close()
    }
}
