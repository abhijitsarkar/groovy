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

package name.abhijitsarkar.moviemanager.service.index.analysis

import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.TokenStream
import org.apache.lucene.analysis.core.LowerCaseFilter
import org.apache.lucene.analysis.pattern.PatternReplaceCharFilter
import org.apache.lucene.util.Version

import java.util.regex.Pattern

/**
 * @author Abhijit Sarkar
 */
class NameAnalyzer extends Analyzer {
    private final Version matchVersion
    private final pattern
    private final replacement

    private static final DEFAULT_PATTERN = Pattern.compile('[^a-zA-Z0-9]?+')
    private static final DEFAULT_REPLACEMENT = ' '

    NameAnalyzer(matchVersion) {
        this(matchVersion, DEFAULT_PATTERN, DEFAULT_REPLACEMENT)
    }

    NameAnalyzer(matchVersion, pattern, replacement) {
        this.matchVersion = matchVersion
        this.pattern = pattern
        this.replacement = replacement
    }

    @Override
    protected Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader) {
        TokenStream src = new PipeTokenizer(matchVersion, reader)
        TokenStream tok = new LowerCaseFilter(matchVersion, src)

        new Analyzer.TokenStreamComponents(src, tok)
    }

    @Override
    protected Reader initReader(fieldName, reader) {
        new PatternReplaceCharFilter(pattern, replacement, reader)
    }
}
