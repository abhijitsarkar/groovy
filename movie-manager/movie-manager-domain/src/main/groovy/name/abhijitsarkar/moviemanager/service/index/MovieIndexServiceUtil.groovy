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
import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.core.SimpleAnalyzer
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexWriterConfig
import org.apache.lucene.store.Directory
import org.apache.lucene.util.Version
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.enterprise.context.RequestScoped
import javax.enterprise.inject.Disposes
import javax.enterprise.inject.Produces
import javax.inject.Inject
/**
 * @author Abhijit Sarkar
 */
@RequestScoped
class MovieIndexServiceUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieIndexServiceUtil)

    @Inject
    @IndexDirectory
    private Directory indexDirectory

    @Inject
    @SearchEngineVersion
    private Version version

    @Produces
    @name.abhijitsarkar.moviemanager.annotation.IndexWriter
    IndexWriter openIndexWriter() {
        LOGGER.info("Opening index writer for directory ${indexDirectory}.")

        IndexWriterConfig iwc = new IndexWriterConfig(version, analyzer)
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE)

        new IndexWriter(indexDirectory, iwc)
    }

    void closeIndexWriter(@Disposes @name.abhijitsarkar.moviemanager.annotation.IndexWriter IndexWriter indexWriter) {
        LOGGER.info("Closing index writer for directory ${indexDirectory}.")

        indexWriter.close()
    }

    private Analyzer getAnalyzer() {
//        new NameAnalyzer(version)
        new SimpleAnalyzer(version)
    }
}
