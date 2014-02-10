package name.abhijitsarkar.moviemanager.service.index.analysis

import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.TokenStream
import org.apache.lucene.analysis.core.KeywordTokenizer
import org.apache.lucene.analysis.core.LowerCaseFilter
import org.apache.lucene.analysis.miscellaneous.TrimFilter
import org.apache.lucene.analysis.pattern.PatternReplaceFilter
import org.apache.lucene.util.Version

import java.util.regex.Pattern

/**
 * @author Abhijit Sarkar
 */
class NameAnalyzer extends Analyzer {
    private final Version matchVersion
    private final Pattern pattern
    private final String replacement
    private static final Pattern DEFAULT_PATTERN = ~/[^a-zA-Z0-9\s+]/
    private static final String DEFAULT_REPLACEMENT = ''

    NameAnalyzer(Version matchVersion) {
        this(matchVersion, DEFAULT_PATTERN, DEFAULT_REPLACEMENT)
    }

    NameAnalyzer(Version matchVersion, Pattern pattern, String replacement) {
        this.matchVersion = matchVersion
        this.pattern = pattern
        this.replacement = replacement
    }

    @Override
    protected Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader) {
        KeywordTokenizer src = new KeywordTokenizer(reader)
        TokenStream tok = new LowerCaseFilter(matchVersion, src)
        tok = new PatternReplaceFilter(tok, pattern, replacement, true)
        tok = new TrimFilter(matchVersion, tok)

        new Analyzer.TokenStreamComponents(src, tok)
    }
}
