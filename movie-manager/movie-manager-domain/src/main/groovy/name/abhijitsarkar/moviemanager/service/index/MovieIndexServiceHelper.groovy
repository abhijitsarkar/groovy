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
import org.apache.lucene.store.FSDirectory
import org.apache.lucene.util.Version
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import javax.enterprise.context.Dependent
import javax.inject.Inject
/**
 * @author Abhijit Sarkar
 */
@Dependent
class MovieIndexServiceHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieIndexServiceHelper)

    @Inject
    @IndexDirectory
    private Directory indexDirectory

    @Inject
    @SearchEngineVersion
    private Version version

    private IndexWriter indexWriter

    @PostConstruct
    void postConstruct() {
        indexWriter = newIndexWriter()
    }

    protected IndexWriter newIndexWriter() {
        LOGGER.info('Creating index writer for directory {}.', indexDirectory)

        IndexWriterConfig iwc = new IndexWriterConfig(version, analyzer)
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE)

        indexWriter = new IndexWriter(indexDirectory, iwc)
    }

    protected Analyzer getAnalyzer() {
//        new NameAnalyzer(version)
        new SimpleAnalyzer(version)
    }

    @PreDestroy
    void preDestroy() {
        LOGGER.info('Closing index writer for directory {}.', indexDirectory)

        indexWriter?.close()
    }

    String getDirectory() {
        if (indexDirectory instanceof FSDirectory) {
            return indexDirectory.directory.absolutePath
        }

        indexDirectory.toString()
    }
}
