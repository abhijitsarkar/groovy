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
import name.abhijitsarkar.moviemanager.annotation.IndexDirectory
import name.abhijitsarkar.moviemanager.annotation.SearchEngineVersion
import name.abhijitsarkar.moviemanager.service.index.analysis.NameAnalyzer
import org.apache.log4j.Logger
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexWriterConfig
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
class MovieIndexServiceUtil {
    private static logger = Logger.getInstance(MovieIndexServiceUtil.class)

    @Inject
    @IndexDirectory
    private Directory indexDirectory

    @Inject
    @SearchEngineVersion
    private Version version

    @Produces
    @name.abhijitsarkar.moviemanager.annotation.IndexWriter
    IndexWriter openIndexWriter() {
        logger.info("Indexing to directory ${indexDirectory}, files with extension ${includeFileExtensions}")

        def dir = FSDirectory.open(new File(indexDirectory))
        def iwc = new IndexWriterConfig(version, getAnalyzer())
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE)

        new IndexWriter(dir, iwc)
    }

    void closeIndexWriter(@Disposes @name.abhijitsarkar.moviemanager.annotation.IndexWriter IndexWriter indexWriter) {
        indexWriter.close()
    }

    private getAnalyzer() {
        new NameAnalyzer(version)
    }
}
