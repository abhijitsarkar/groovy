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


package name.abhijitsarkar.moviedatabase.service.index.analysis

import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.BaseTokenStreamTestCase
import org.apache.lucene.util.Version
import org.junit.Test

/**
 * @author Abhijit Sarkar
 */
class NameAnalyzerTest {
    private final Analyzer nameAnalyzer

    NameAnalyzerTest() {
        nameAnalyzer = new NameAnalyzer(Version.LUCENE_46)
    }

    @Test
    void testRemoveSpecialCharacters() {
        String input = 'a\'B- 98;'
        String[] output = ['ab 98'] as String[]

        BaseTokenStreamTestCase.assertAnalyzesTo(nameAnalyzer, input, output)
    }
}
