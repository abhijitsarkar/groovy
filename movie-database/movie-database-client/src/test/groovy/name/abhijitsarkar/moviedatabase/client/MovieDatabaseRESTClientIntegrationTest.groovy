package name.abhijitsarkar.moviedatabase.client

import name.abhijitsarkar.moviedatabase.MovieDatabaseApplication
import name.abhijitsarkar.moviedatabase.domain.MovieRip
import name.abhijitsarkar.moviedatabase.service.index.IndexField
import name.abhijitsarkar.moviedatabase.test.integration.PatchedSpringApplicationContextLoader
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.RestTemplates
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

/**
 * @author Abhijit Sarkar
 */
@RunWith(SpringJUnit4ClassRunner)
@ContextConfiguration(value = ['classpath:client-config.groovy', 'classpath:integ-test-config.groovy'],
        loader = PatchedSpringApplicationContextLoader)
@SpringApplicationConfiguration(classes = MovieDatabaseApplication)
@WebAppConfiguration
@IntegrationTest
class MovieDatabaseRESTClientIntegrationTest {
    private static final String SERVICE_URL = 'http://localhost:8080/movies'

    private RestTemplate restTemplate

    MovieDatabaseRESTClientIntegrationTest() {
        restTemplate = RestTemplates.get()
    }

    @Before
    void setUp() {
        String movieDir = new File(new ClassPathResource('movies').URL.toURI()).absolutePath
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>()
        requestBody.add('dir', movieDir)

        HttpHeaders headers = newHttpHeaders(MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON)
        HttpEntity<Map<String, String>> request = new HttpEntity<Map<String, String>>(requestBody, headers)

        ResponseEntity<String> entity = restTemplate.exchange("${SERVICE_URL}", HttpMethod.POST, request, String)

        println 'setUp' + entity.body
    }

    private newHttpHeaders(MediaType contentType, MediaType accept) {
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(contentType)
        headers.setAccept([accept])
    }

    @Test
    void testSearchByTitle() {
        HttpHeaders headers = newHttpHeaders(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON)

        ResponseEntity<String> entity = restTemplate.exchange("${SERVICE_URL}/title/memento",
                HttpMethod.GET, null, String)

        assert entity?.statusCode == HttpStatus.OK
    }

    @Test
    void testAdvancedSearchByReleaseDate() {
        HttpHeaders headers = newHttpHeaders(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON)

        String searchText = "${IndexField.RELEASE_DATE.name()}:[2001 TO 2001]"

        ResponseEntity<String> entity = restTemplate.exchange("${SERVICE_URL}/${searchText}",
                HttpMethod.GET, null, String)

        assert entity?.statusCode == HttpStatus.OK
    }

    @Test
    void testNoSuchMovie() {
        HttpHeaders headers = newHttpHeaders(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON)

        ResponseEntity<String> entity = restTemplate.exchange("${SERVICE_URL}/title/abc",
                HttpMethod.GET, null, String)

        assert entity?.statusCode == HttpStatus.NO_CONTENT
    }
}
