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

package name.abhijitsarkar.moviemanager.util

import name.abhijitsarkar.moviemanager.service.indexandsearch.MovieIndexAndSearchServicesTest
import name.abhijitsarkar.moviemanager.service.rip.MovieRipServiceTest
import org.apache.deltaspike.cdise.api.CdiContainer
import org.apache.deltaspike.cdise.api.CdiContainerLoader
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.experimental.categories.Categories
import org.junit.runner.RunWith
import org.junit.runners.Suite

/**
 * @author Abhijit Sarkar
 */
@RunWith(Categories)
@Categories.IncludeCategory(CDISuiteTest)
@Suite.SuiteClasses([
MovieIndexAndSearchServicesTest,
MovieRipServiceTest
])
class CDITestSuite {
    static CdiContainer container

    @BeforeClass
    static void newContainer() {
        container = CdiContainerLoader.getCdiContainer();

// now we gonna boot the CDI container. This will trigger the classpath scan, etc
        container.boot();

// and finally we like to start all built-in contexts
        container.getContextControl().startContexts();
    }

    @AfterClass
    static void destroyContainer() {
        container.shutdown()
    }
}
