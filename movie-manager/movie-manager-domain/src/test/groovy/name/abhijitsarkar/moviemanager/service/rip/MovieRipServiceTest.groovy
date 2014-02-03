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

import mockit.Mocked
import mockit.NonStrictExpectations
import name.abhijitsarkar.moviemanager.annotation.MovieGenres
import name.abhijitsarkar.moviemanager.domain.MovieRip
import name.abhijitsarkar.moviemanager.util.AbstractCDITest
import org.junit.Test

import javax.inject.Inject

//@org.junit.experimental.categories.Category(CDISuiteTest)
class MovieRipServiceTest extends AbstractCDITest {
    @Inject
    private MovieRipService service

    @Inject
    @MovieGenres
    private List<String> genres

    @Test
    void testGetFileExtension() {
        assert '.txt' == service.getFileExtension('file.txt')
        assert '.txt' == service.getFileExtension('.txt')
        assert '' == service.getFileExtension('file')
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
    void testParseMovieRip() {
        MovieRip mr = service.parseMovieRip('Casino Royal (2006).mkv')

        assert mr.title == 'Casino Royal'
        assert mr.releaseDate[Calendar.YEAR] == 2006
        assert mr.fileExtension == '.mkv'

        mr = service.parseMovieRip('2 Fast 2 Furious - part 1 (2001).mkv')

        assert mr.title == '2 Fast 2 Furious - part 1'
        assert mr.releaseDate[Calendar.YEAR] == 2001
        assert mr.fileExtension == '.mkv'

        mr = service.parseMovieRip('He-Man - A Friend In Need.avi')

        assert mr.title == 'He-Man - A Friend In Need'
        assert mr.releaseDate[Calendar.YEAR] == 1
        assert mr.fileExtension == '.avi'
    }

    @Test
    void testGetParent(@Mocked final File f) {
        // Test when file is the root directory and has no parent
        new NonStrictExpectations() {
            {
                f.parentFile; result = (File) any
                // Record the result of 2 consecutive isDirectory() calls
                f.isDirectory(); result = [false, false]
                f <=> ((File) any); result = -1
            }
        }

        assert 'immediateParent' == service.getParent(f, 'currentGenre', null, 'immediateParent')

        assert 'immediateParent' == service.getParent(f, 'currentGenre', null, 'immediateParent')
    }

//    @Test
//    void testGetParentWhenParentIsGenre(@Cascading File f) {
//        new NonStrictExpectations() {
//            {
//                f.isDirectory(); result = Boolean.TRUE
//                f.compareTo((File) withNull()); result = 1
//                f.name; result = ['currentGenre', 'movieRip']
//            }
//        }
//
//        assert 'movieRip' == service.getParent(f, 'currentGenre', null, 'immediateParent')
//    }
}
