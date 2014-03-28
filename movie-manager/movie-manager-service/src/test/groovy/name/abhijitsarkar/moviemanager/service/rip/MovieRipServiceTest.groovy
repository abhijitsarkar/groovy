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

package name.abhijitsarkar.moviemanager.service.rip

import name.abhijitsarkar.moviemanager.domain.MovieRipFileExtension
import org.gmock.WithGMock
import org.junit.Test

@WithGMock
class MovieRipServiceTest {
    private MovieRipService service
    private List<String> genres = [
            'Action and Adventure',
            'Animation',
            'Comedy',
            'Documentary',
            'Drama',
            'Horror',
            'R-Rated Mainstream Movies',
            'Romance',
            'Sci-Fi',
            'Thriller',
            'X-Rated'
    ]

    private List<String> includes = MovieRipFileExtension.values().collect {
        // GOTCHA ALERT: GString is not equal to String; "a" != 'a'
        ".${it.name().toLowerCase()}".toString()
    }

    MovieRipServiceTest() {
        service = new MovieRipService()
        service.genres = this.genres
        service.includes = this.includes
    }

    @Test
    void testIsGenre() {
        genres.each {
            assert service.isGenre(it)
        }

        assert !service.isGenre('file.txt')
    }

    @Test
    void testIsMovieRip() {
        [
                'abc.aVi',
                'abc.MKV',
                'abc.mp4',
                'abc.divx',
                'abc.mov'
        ].each {
            assert service.isMovieRip(it)
        }

        assert !service.isMovieRip('file.txt')
    }

    @Test
    void testGetParentWhenParentIsNotDirectory() {
        File f = mock(File)
        File parentFile = mock(File)

        f.parentFile.returns(parentFile)

        parentFile.isDirectory().returns(false)

        play {
            assert 'immediateParent' == service.getParent(f, 'currentGenre', null, 'immediateParent')
        }
    }

    @Test
    void testGetParentWhenParentIsRoot() {
        File f = mock(File)
        File parentFile = mock(File)

        f.parentFile.returns(parentFile)

        parentFile.isDirectory().returns(true)
        parentFile.compareTo(null).returns(-1)

        play {
            assert 'immediateParent' == service.getParent(f, 'currentGenre', null, 'immediateParent')
        }
    }

    @Test
    void testGetParentWhenParentIsGenre() {
        File f = mock(File)
        File parentFile = mock(File)

        f.parentFile.returns(parentFile)

        parentFile.isDirectory().returns(true)
        parentFile.compareTo(null).returns(1)
        parentFile.name.returns('currentGenre')
        f.isDirectory().returns(true)
        f.name.returns('Sci-Fi')

        play {
            assert 'Sci-Fi' == service.getParent(f, 'currentGenre', null, 'immediateParent')
        }
    }
}
