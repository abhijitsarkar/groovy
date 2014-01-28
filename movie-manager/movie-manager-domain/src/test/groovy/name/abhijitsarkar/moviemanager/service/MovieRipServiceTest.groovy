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

package name.abhijitsarkar.moviemanager.service

import mockit.Cascading
import mockit.Injectable
import mockit.Mocked
import mockit.NonStrictExpectations
import org.junit.Before
import org.junit.Test

class MovieRipServiceTest {
	def service

	@Before
	void setUp() {
		service = new MovieRipService(genreList())
	}

    def genreList() {
        [
            'Action and Adventure',
            'Animation',
            'Comedy' ,
            'Documentary',
            'Drama',
            'Horror',
            'R-Rated Mainstream Movies',
            'Romance',
            'Sci-Fi',
            'Thriller',
            'X-Rated'
        ]
    }

	@Test
	void testGetFileExtension() {
		assert 'txt' == service.getFileExtension('file.txt')
		assert 'txt' == service.getFileExtension('.txt')
		assert '' == service.getFileExtension('file')
	}

	@Test
	void testIsGenre() {
		this.genreList().each {
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
		def mr = service.parseMovieRip('Casino Royal (2006).mkv')

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
		new NonStrictExpectations() { {
						f.parentFile; result = (File) any
						// Record the result of 2 consecutive isDirectory() calls
						f.isDirectory(); result = [false, false]
						f.compareTo((File) any); result = -1
					}
				}

		assert 'immediateParent' == service.getParent(f, 'currentGenre', null, 'immediateParent')

		assert 'immediateParent' == service.getParent(f, 'currentGenre', null, 'immediateParent')
	}

	// Test when parent file is a genre directory
	//	@Test
	//	void testGetParentWhenParentIsGenre() {
	//		new MockUp<Comparable<File>>() {
	//			@Mock int compareTo(File someFile) { 1 }
	//		}
	//
	//		File parent = new MockUp<File>() {
	//			@Mock boolean isDirectory() { true }
	//			@Mock String getName() {'parent'}
	//		}
	//		File f = new MockUp<File>() {
	//			@Mock File getParentFile() { parent }
	//			@Mock boolean isDirectory() { true }
	//			@Mock String getName() {'movieRip'}
	//		}
	//
	//		assert 'movieRip' == service.getParent(f, 'currentGenre', 'immediateParent', null)
	//	}

//	@Test
//	void testGetParentWhenParentIsGenre(@Cascading final File f) {
//		new NonStrictExpectations() { {
//						f.isDirectory(); result = [true, true]
//						f.compareTo((File) any); result = 1
//						f.name; result = ['currentGenre', 'movieRip']
//					}
//				}
//
//		assert 'movieRip' == service.getParent(f, 'currentGenre', null, 'immediateParent')
//	}
}
