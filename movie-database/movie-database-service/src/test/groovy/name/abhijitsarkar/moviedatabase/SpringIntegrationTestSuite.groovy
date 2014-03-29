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

package name.abhijitsarkar.moviedatabase

import name.abhijitsarkar.moviedatabase.service.facade.MovieFacadeIntegrationTest
import name.abhijitsarkar.moviedatabase.service.index.MovieIndexServiceIntegrationTest
import name.abhijitsarkar.moviedatabase.service.rip.MovieRipServiceIntegrationTest
import name.abhijitsarkar.moviedatabase.service.search.MovieSearchServiceIntegrationTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * @author Abhijit Sarkar
 */
@RunWith(Suite)
@Suite.SuiteClasses([MovieRipServiceIntegrationTest, MovieIndexServiceIntegrationTest,
        MovieSearchServiceIntegrationTest, MovieFacadeIntegrationTest])
class SpringIntegrationTestSuite {
}
