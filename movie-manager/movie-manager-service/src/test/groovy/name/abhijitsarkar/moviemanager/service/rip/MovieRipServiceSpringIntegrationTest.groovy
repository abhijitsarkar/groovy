package name.abhijitsarkar.moviemanager.service.rip

import name.abhijitsarkar.moviemanager.AbstractSpringIntegrationTest
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author Abhijit Sarkar
 */
class MovieRipServiceSpringIntegrationTest extends AbstractSpringIntegrationTest {
    @Autowired
    private MovieRipService movieRipService

    @Test
    void testGenres() {
        assert 'Horror' in movieRipService.genres
    }

    @Test
    void testIncludes() {
        assert '.mkv' in movieRipService.includes
    }
}
