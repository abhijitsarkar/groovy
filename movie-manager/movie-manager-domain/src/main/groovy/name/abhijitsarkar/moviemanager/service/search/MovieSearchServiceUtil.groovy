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
import name.abhijitsarkar.moviemanager.annotation.IndexDirectory
import name.abhijitsarkar.moviemanager.annotation.SearchEngineVersion
import name.abhijitsarkar.moviemanager.service.index.analysis.NameAnalyzer
import org.apache.log4j.Logger
import org.apache.lucene.index.DirectoryReader
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.store.Directory
import org.apache.lucene.store.FSDirectory
import org.apache.lucene.util.Version

import javax.annotation.ManagedBean
import javax.enterprise.inject.Disposes
import javax.enterprise.inject.Produces
import javax.inject.Inject
/**
 * @author Abhijit Sarkar
 */
@ManagedBean
//@ApplicationScoped
class MovieSearchServiceUtil {
    private static logger = Logger.getInstance(MovieSearchServiceUtil.class)

    @Inject
    @IndexDirectory
    private Directory indexDirectory

    @Inject
    @SearchEngineVersion
    private Version version

    @Produces
    @name.abhijitsarkar.moviemanager.annotation.IndexSearcher
    IndexSearcher createSearcher() {
        new IndexSearcher(indexReader())
    }

    private indexReader() {
        logger.info("Searching in the directory ${indexDirectory}")

        def dir = FSDirectory.open(new File(indexDirectory))
        DirectoryReader.open(dir)
    }

    void closeIndexReader(
            @Disposes @name.abhijitsarkar.moviemanager.annotation.IndexSearcher IndexSearcher indexSearcher) {
        indexSearcher.indexReader.close()
    }

    @Produces
    @name.abhijitsarkar.moviemanager.annotation.QueryParser
    StandardQueryParser createQueryParser() {
        new StandardQueryParser(getAnalyzer())
    }

    private getAnalyzer() {
        new NameAnalyzer(version)
    }
}
