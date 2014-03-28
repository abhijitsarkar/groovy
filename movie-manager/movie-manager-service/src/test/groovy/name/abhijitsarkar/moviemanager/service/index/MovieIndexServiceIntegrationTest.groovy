package name.abhijitsarkar.moviemanager.service.index

import name.abhijitsarkar.moviemanager.AbstractSpringIntegrationTest
import name.abhijitsarkar.moviemanager.service.index.MovieIndexService
import org.apache.lucene.store.RAMDirectory
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author Abhijit Sarkar
 */
class MovieIndexServiceIntegrationTest extends AbstractSpringIntegrationTest {
    @Autowired
    private MovieIndexService movieIndexService

    @Test
    void testNotNull() {
        assert movieIndexService?.indexDirectory
        assert movieIndexService?.version
    }

    @Test
    void 'test that index directory is overridden correctly'() {
        assert movieIndexService?.indexDirectory instanceof RAMDirectory
    }
}
