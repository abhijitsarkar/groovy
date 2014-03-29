/*
 * Copyright (c) ${date}, the original author or authors.
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


import org.gmock.WithGMock
import org.junit.Test

@WithGMock
class MovieRipServiceTest {
    private ConfigObject config

    private MovieRipService service

    MovieRipServiceTest() {
        service = new MovieRipService()

        config = new ConfigSlurper().parse(getClass().getResource('/config.groovy'))

        service.genres = config.genres
        service.includes = config.includes
    }

    @Test
    void testIsGenre() {
        config.genres.each {
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
