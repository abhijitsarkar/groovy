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


package name.abhijitsarkar.moviedatabase.test.integration.client

import name.abhijitsarkar.moviedatabase.MovieDatabaseApplication
import name.abhijitsarkar.moviedatabase.service.index.IndexField
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.TestRestTemplate
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

/**
 * @author Abhijit Sarkar
 */
@RunWith(SpringJUnit4ClassRunner)
//@ContextConfiguration(value = ['classpath:client-config.groovy', 'classpath:integ-test-config.groovy'],
//        classes = [MovieDatabaseApplication],
//        loader = SpringApplicationContextLoader)
@SpringApplicationConfiguration(locations = ['classpath:client-config.groovy', 'classpath:integ-test-config.groovy'],
        classes = [MovieDatabaseApplication])
@WebAppConfiguration
@IntegrationTest
class MovieDatabaseRESTClientIntegrationTest {
    private static final String SERVICE_URL = 'http://localhost:8080/movies'

    private TestRestTemplate restTemplate = new TestRestTemplate()

    @Before
    void setUp() {
        String movieDir = new File(new ClassPathResource('movies').URL.toURI()).absolutePath
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>()
        requestBody.add('dir', movieDir)

        HttpHeaders headers = newHttpHeaders(MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON)
        HttpEntity<Map<String, String>> request = new HttpEntity<Map<String, String>>(requestBody, headers)

        ResponseEntity<String> entity = restTemplate.exchange("${SERVICE_URL}", HttpMethod.POST, request, String)
    }

    private newHttpHeaders(MediaType contentType, MediaType accept) {
        HttpHeaders headers = new HttpHeaders()
//        headers.setContentType(contentType)
        headers.setAccept([accept])
    }

    @Test
    void testSearchByTitle() {
        ResponseEntity<String> entity = restTemplate.exchange("${SERVICE_URL}/title/memento",
                HttpMethod.GET, null, String)

        assert entity?.statusCode == HttpStatus.OK
    }

    @Test
    void testAdvancedSearchByReleaseDate() {
        String searchText = "${IndexField.RELEASE_DATE.name()}:[2001 TO 2001]"

        ResponseEntity<String> entity = restTemplate.exchange("${SERVICE_URL}/search?q=${searchText}",
                HttpMethod.GET, null, String)

        assert entity?.statusCode == HttpStatus.OK
    }

    @Test
    void testNoSuchTitle() {
        ResponseEntity<String> entity = restTemplate.exchange("${SERVICE_URL}/title/abc",
                HttpMethod.GET, null, String)

        assert entity?.statusCode == HttpStatus.NO_CONTENT
    }

    @Test
    void testNoSuchMovie() {
        String searchText = "${IndexField.RELEASE_DATE.name()}:[2350 TO 2351]"

        ResponseEntity<String> entity = restTemplate.exchange("${SERVICE_URL}/search?q=${searchText}",
                HttpMethod.GET, null, String)


        assert entity?.statusCode == HttpStatus.NO_CONTENT
    }
}
