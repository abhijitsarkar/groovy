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


package name.abhijitsarkar.moviedatabase.service.rip

import groovy.transform.PackageScope
import name.abhijitsarkar.moviedatabase.domain.Movie
import name.abhijitsarkar.moviedatabase.domain.MovieRip
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.util.regex.Matcher

import static name.abhijitsarkar.moviedatabase.service.rip.MovieNameMatcherCategory.MOVIE_NAME_WITH_RELEASE_YEAR_REGEX

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
