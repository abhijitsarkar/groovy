package name.abhijitsarkar.moviemanager

import name.abhijitsarkar.moviemanager.service.facade.MovieFacadeSpringIntegrationTest
import name.abhijitsarkar.moviemanager.service.index.analysis.MovieIndexServiceSpringIntegrationTest
import name.abhijitsarkar.moviemanager.service.rip.MovieRipServiceSpringIntegrationTest
import name.abhijitsarkar.moviemanager.service.search.MovieSearchServiceSpringIntegrationTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * @author Abhijit Sarkar
 */
@RunWith(Suite)
@Suite.SuiteClasses([MovieRipServiceSpringIntegrationTest, MovieIndexServiceSpringIntegrationTest,
        MovieSearchServiceSpringIntegrationTest, MovieFacadeSpringIntegrationTest])
class SpringIntegrationTestSuite {
}
