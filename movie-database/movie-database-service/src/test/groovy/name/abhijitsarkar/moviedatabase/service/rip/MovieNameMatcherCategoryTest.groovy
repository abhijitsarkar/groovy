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

import org.junit.BeforeClass
import org.junit.Test

import java.util.regex.Matcher

import static name.abhijitsarkar.moviedatabase.service.rip.MovieNameMatcherCategory.MOVIE_NAME_WITH_RELEASE_YEAR_REGEX
/**
 * @author Abhijit Sarkar
 */
class MovieNameMatcherCategoryTest {
    @BeforeClass
    static void oneTimeSetUp() {
        Matcher.mixin MovieNameMatcherCategory
    }

    @Test
    void testMatch() {
        Matcher m = 'Casino Royal (2006).mkv' =~ MOVIE_NAME_WITH_RELEASE_YEAR_REGEX
        assert m.matches()

        assert m.title == 'Casino Royal'
        assert m.year == 2006

        m.reset()
        m = '2 Fast 2 Furious - part 1 (2001).mkv' =~ MOVIE_NAME_WITH_RELEASE_YEAR_REGEX
        assert m.matches()

        assert m.title == '2 Fast 2 Furious - part 1'
        assert m.year == 2001
    }

    @Test
    void testNoMatch() {
        Matcher m = 'He-Man - A Friend In Need.avi' =~ MOVIE_NAME_WITH_RELEASE_YEAR_REGEX
        assert !m.matches()
    }
}
