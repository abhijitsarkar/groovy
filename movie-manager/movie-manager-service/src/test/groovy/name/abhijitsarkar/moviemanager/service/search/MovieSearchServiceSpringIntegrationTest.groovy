package name.abhijitsarkar.moviemanager.service.search

import name.abhijitsarkar.moviemanager.AbstractSpringIntegrationTest
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author Abhijit Sarkar
 */
class MovieSearchServiceSpringIntegrationTest extends AbstractSpringIntegrationTest {
    @Autowired
    private MovieSearchService movieSearchService

    @Test
    void testNotNull() {
        assert movieSearchService?.indexDirectory
    }
}
