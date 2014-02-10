package name.abhijitsarkar.moviemanager.service.index.analysis
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
