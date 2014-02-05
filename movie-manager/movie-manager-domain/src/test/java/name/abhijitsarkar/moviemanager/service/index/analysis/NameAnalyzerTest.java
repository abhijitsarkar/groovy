package name.abhijitsarkar.moviemanager.service.index.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.BaseTokenStreamTestCase;
import org.apache.lucene.util.Version;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Abhijit Sarkar
 */
public class NameAnalyzerTest {
    private final Analyzer nameAnalyzer;

    public NameAnalyzerTest() {
        nameAnalyzer = new NameAnalyzer(Version.LUCENE_46);
    }

    @Test
    public void testRemoveSpecialCharacters() throws IOException {
        String input = "a'b- 98;";
        String[] output = new String[]{"ab 98"};

        BaseTokenStreamTestCase.assertAnalyzesTo(nameAnalyzer, input, output);
    }
}
