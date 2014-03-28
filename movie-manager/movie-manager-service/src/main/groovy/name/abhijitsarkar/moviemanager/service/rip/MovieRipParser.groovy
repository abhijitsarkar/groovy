package name.abhijitsarkar.moviemanager.service.rip

import groovy.transform.PackageScope
import name.abhijitsarkar.moviemanager.domain.Movie
import name.abhijitsarkar.moviemanager.domain.MovieRip
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.util.regex.Matcher

import static name.abhijitsarkar.moviemanager.service.rip.MovieNameMatcherCategory.MOVIE_NAME_WITH_RELEASE_YEAR_REGEX

/**
 * @author Abhijit Sarkar
 */
class MovieRipParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieRipParser)

    static MovieRip parseMovieRip(String fileName) {
        String movieTitle
        int year = 0
        final String fileExtension = fileExtension(fileName)

        use(MovieNameMatcherCategory) {
            final Matcher matcher = fileName =~ MOVIE_NAME_WITH_RELEASE_YEAR_REGEX

            if (matcher.matches()) {
                movieTitle = matcher.title
                year = matcher.year
            } else {
                // Couldn't parse file name, extract as-is without file extension
                movieTitle = fileName.minus(fileExtension)
            }
        }

        final Movie m = new Movie(title: movieTitle, releaseDate: releaseDate(year))

        final MovieRip mr = new MovieRip(m)
        mr.fileExtension = fileExtension

        LOGGER.debug('Created movie rip {}.', mr)

        mr
    }

    static String fileExtension(fileName) {
        final String fileExtension = fileName.tokenize('.').last()

        fileExtension != fileName ? ".${fileExtension}".toString() : ''
    }

    @PackageScope
    static Date releaseDate(final int year) {
        Date.parse('MM/dd/yyyy', "01/01/${year}")
    }
}
