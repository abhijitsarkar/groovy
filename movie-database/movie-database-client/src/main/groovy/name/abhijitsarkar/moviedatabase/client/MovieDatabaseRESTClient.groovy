/*
 * Copyright (c) 2014, the original author or authors.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * A copy of the GNU General Public License accompanies this software,
 * and is also available at http://www.gnu.org/licenses.
 */


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
        final String indexDirectory = movieFacade.index(movieDirectory)

        "{\"Index directory\":\"$indexDirectory\"}"
    }

    @RequestMapping
    Collection<MovieRip> fetchAll() {
        String message = 'No movies were found.'

        respond(movieFacade.fetchAll(), message)
    }

    @RequestMapping(value = '/search')
    Collection<MovieRip> advancedSearch(@RequestParam('q') String searchText) {
        String message = "No movies were found corresponding to search text: ${searchText}."

        respond(movieFacade.advancedSearch(searchText), message)
    }

    @RequestMapping(value = '/{searchField}/{searchText}')
    Collection<MovieRip> searchByField(@PathVariable('searchField') String indexField,
                                       @PathVariable('searchText') String searchText) {
        String message = "No movies were found corresponding to index field: " +
                "${indexField} and search text: ${searchText}."

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
