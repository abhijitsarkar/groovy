package name.abhijitsarkar.moviedatabase.client

import name.abhijitsarkar.moviedatabase.domain.MovieRip
import name.abhijitsarkar.moviedatabase.service.facade.MovieFacade
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.web.bind.annotation.RequestMethod.GET
import static org.springframework.web.bind.annotation.RequestMethod.POST

/**
 * @author Abhijit Sarkar
 */
@RestController
@RequestMapping(value = '/movies', method = GET, produces = APPLICATION_JSON_VALUE)
class MovieDatabaseRESTClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieDatabaseRESTClient)

    @Autowired
    private MovieFacade movieFacade

    @RequestMapping(method = POST, consumes = APPLICATION_FORM_URLENCODED_VALUE)
    String index(@RequestParam('dir') String movieDirectory) {
        movieFacade.index(movieDirectory)
    }

    @RequestMapping
    Collection<MovieRip> fetchAll() {
        String message = 'No movies were found.'

        respond(movieFacade.fetchAll(), message)
    }

    @RequestMapping(value = '/{searchText}')
    Collection<MovieRip> advancedSearch(@PathVariable('searchText') String searchText) {
        String message = "No movies were found corresponding to search text: ${searchText}."

        respond(movieFacade.advancedSearch(searchText), message)
    }

    @RequestMapping(value = '/{searchField}/{searchText}')
    Collection<MovieRip> searchByField(@PathVariable('searchField') String indexField,
                                @PathVariable('searchText') String searchText) {
        String message = "No movies were found corresponding to index field: ${indexField} and search text: ${searchText}."

        respond(movieFacade.searchByField(searchText, indexField), message)
    }

    @RequestMapping(value = '/indexDirectory')
    String indexDirectory() {
        movieFacade.indexDirectory()
    }

    @RequestMapping(value = '/searchDirectory')
    String searchDirectory() {
        movieFacade.searchDirectory()
    }

    private Collection<MovieRip> respond(Collection<MovieRip> movieRips, String message) {
        if (!movieRips) {
            LOGGER.warn(message)

            throw new NoMoviesFoundException(message)
        }

        movieRips
    }

    @ExceptionHandler(NoMoviesFoundException)
    ResponseEntity<String> handleNoMoviesFound(NoMoviesFoundException e) {
        new ResponseEntity<String>(HttpStatus.NO_CONTENT)
    }
}
