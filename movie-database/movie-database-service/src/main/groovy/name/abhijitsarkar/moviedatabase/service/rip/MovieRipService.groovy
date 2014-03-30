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

/**
 * @author Abhijit Sarkar
 */

package name.abhijitsarkar.moviedatabase.service.rip

import name.abhijitsarkar.moviedatabase.domain.MovieRip
import name.abhijitsarkar.moviedatabase.service.validation.ValidDirectory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

import javax.annotation.PostConstruct
import javax.annotation.Resource

import static name.abhijitsarkar.moviedatabase.service.rip.MovieRipParser.fileExtension
import static name.abhijitsarkar.moviedatabase.service.rip.MovieRipParser.parseMovieRip

@Service
class MovieRipService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieRipService)

    @Resource
    Collection<String> genres

    @Resource
    Collection<String> includes

    @PostConstruct
    void postConstruct() {
        LOGGER.debug('All genres {}.', genres)
        LOGGER.debug('Included files {}.', includes)
    }

    Collection<MovieRip> getMovieRips(
            @ValidDirectory(message = 'Movie directory is not valid.') String movieDirectory) {
        LOGGER.debug('Indexing movies from {}.', movieDirectory)

        File rootDir = new File(movieDirectory)

        if (!rootDir.isAbsolute()) {
            LOGGER.warn('Path {} is not absolute and is resolved to {}.', movieDirectory, rootDir.absolutePath)
        }

        String currentGenre
        Collection<MovieRip> movieRips = [] as SortedSet

        rootDir.eachFileRecurse { File f ->
            delegate = this

            LOGGER.trace('Found file, path {}, name {}.', f.absolutePath, f.name)

            if (f.isDirectory() && isGenre(f.name)) {
                LOGGER.debug('Setting current genre to {}.', f.name)

                currentGenre = f.name
            } else if (isMovieRip(f.name)) {
                MovieRip movieRip = parseMovieRip(f.name)

                movieRip.genres = (movieRip.genres ?: [] as Set)

                movieRip.genres << currentGenre

                movieRip.fileSize = f.size()

                String parent = getParent(f, currentGenre, rootDir)

                if (!currentGenre?.equalsIgnoreCase(parent)) {
                    movieRip.parent = parent
                }

                LOGGER.info('Found movie {}.', movieRip)

                boolean isUnique = movieRips.add(movieRip)

                if (!isUnique) {
                    LOGGER.info('Found duplicate movie {}.', movieRip)
                }
            }
        }

        movieRips
    }

    boolean isMovieRip(final String fileName) {
        fileExtension(fileName).toLowerCase() in includes
    }

    boolean isGenre(final String fileName) {
        fileName in genres
    }

    final String getParent(File file, String currentGenre, File rootDirectory, String immediateParent = null) {
        File parentFile = file.parentFile

        if (!parentFile?.isDirectory() || parentFile?.compareTo(rootDirectory) <= 0) {
            return immediateParent
        }

        if (parentFile.name.equalsIgnoreCase(currentGenre)) {
            if (file.isDirectory()) {
                return file.name
            }
        }

        getParent(parentFile, currentGenre, rootDirectory, parentFile.name)
    }
}