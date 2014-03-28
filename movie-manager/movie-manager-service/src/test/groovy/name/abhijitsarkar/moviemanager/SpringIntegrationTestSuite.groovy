package name.abhijitsarkar.moviemanager

import name.abhijitsarkar.moviemanager.service.facade.MovieFacadeIntegrationTest
import name.abhijitsarkar.moviemanager.service.index.analysis.MovieIndexServiceIntegrationTest
import name.abhijitsarkar.moviemanager.service.rip.MovieRipServiceIntegrationTest
import name.abhijitsarkar.moviemanager.service.search.MovieSearchServiceIntegrationTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * @author Abhijit Sarkar
 */
@RunWith(Suite)
@Suite.SuiteClasses([MovieRipServiceIntegrationTest, MovieIndexServiceIntegrationTest,
        MovieSearchServiceIntegrationTest, MovieFacadeIntegrationTest])
class SpringIntegrationTestSuite {
}
