package name.abhijitsarkar.moviemanager.service.facade

import name.abhijitsarkar.moviemanager.AbstractSpringIntegrationTest
import name.abhijitsarkar.moviemanager.facade.MovieFacade
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author Abhijit Sarkar
 */
class MovieFacadeSpringIntegrationTest extends AbstractSpringIntegrationTest {
    @Autowired
    private MovieFacade movieFacade

    @Test
    void testNotNull() {
        assert movieFacade?.movieRipService
        assert movieFacade?.movieIndexService
        assert movieFacade?.movieSearchService
        assert movieFacade?.queryBuilder
    }
}
