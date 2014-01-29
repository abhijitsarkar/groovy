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

package name.abhijitsarkar.moviemanager.service

import mockit.Mocked
import mockit.NonStrictExpectations
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexWriterConfig
import org.apache.lucene.store.Directory
import org.apache.lucene.store.FSDirectory
import org.apache.lucene.util.Version
import org.junit.Test
/**
 * @author Abhijit Sarkar
 */
class MovieIndexUtilTest {
    def indexUtil

    MovieIndexUtilTest() {
        indexUtil = new MovieIndexUtil()
        indexUtil.indexDirectory = 'indexDirectory'
        indexUtil.version = Version.LUCENE_46
    }

    @Test
    void testOpenIndexWriter(
            @Mocked FSDirectory fsDirectory, @Mocked Directory dir, @Mocked IndexWriterConfig iwc,
            @Mocked File indexDirectory, @Mocked IndexWriter indexWriter) {
        new NonStrictExpectations() {
            {
                new File('indexDirectory'); result = indexDirectory
                FSDirectory.open(indexDirectory); result = dir
                new IndexWriterConfig(Version.LUCENE_46, withInstanceOf(StandardAnalyzer.class)); result = iwc
                iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE)

                new IndexWriter(dir, iwc); result = indexWriter
            }
        }

        assert indexUtil.openIndexWriter()
    }

    @Test
    void testCreateAnalyzer() {
        assert indexUtil.createAnalyzer() instanceof StandardAnalyzer
    }

    @Test
    void testCloseIndexWriter(@Mocked IndexWriter indexWriter) {
        new NonStrictExpectations() {
            {
                indexWriter.close()
            }
        }
        indexUtil.closeIndexWriter(indexWriter)
    }
}
