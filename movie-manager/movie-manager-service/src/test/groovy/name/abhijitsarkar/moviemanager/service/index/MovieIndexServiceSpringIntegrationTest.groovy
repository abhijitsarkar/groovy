package name.abhijitsarkar.moviemanager.service.index.analysis

import name.abhijitsarkar.moviemanager.AbstractSpringIntegrationTest
import name.abhijitsarkar.moviemanager.service.index.MovieIndexService
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author Abhijit Sarkar
 */
class MovieIndexServiceSpringIntegrationTest extends AbstractSpringIntegrationTest {
    @Autowired
    private MovieIndexService movieIndexService

    @Test
    void testNotNull() {
        assert movieIndexService?.indexDirectory
        assert movieIndexService?.version
    }
}
