package name.abhijitsarkar.moviemanager.service.search

import name.abhijitsarkar.moviemanager.AbstractSpringIntegrationTest
import org.apache.lucene.store.RAMDirectory
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author Abhijit Sarkar
 */
class MovieSearchServiceIntegrationTest extends AbstractSpringIntegrationTest {
    @Autowired
    private MovieSearchService movieSearchService

    @Test
    void testNotNull() {
        assert movieSearchService?.indexDirectory
    }

    @Test
    void 'test that index directory is overridden correctly'() {
        assert movieSearchService?.indexDirectory instanceof RAMDirectory
    }
}
