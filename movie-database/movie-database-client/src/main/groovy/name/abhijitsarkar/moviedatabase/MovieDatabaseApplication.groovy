package name.abhijitsarkar.moviedatabase

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan

/**
 * @author Abhijit Sarkar
 */

@EnableAutoConfiguration
@ComponentScan
class MovieDatabaseApplication {
    static void main(String[] args) {
        final Set<Object> sources = [MovieDatabaseApplication, 'classpath:client-config.groovy'] as Set
        final SpringApplication app = new SpringApplication()
        app.setSources(sources)

        app.run(args)
    }
}
