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

import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass

import javax.ejb.embeddable.EJBContainer

/**
 * @author Abhijit Sarkar
 */
class AbstractCDITest {
    protected static EJBContainer container

    // GOTCHA ALERT: The following methods are guaranteed to be called before the corresponding ones in the subclasses
    // but ONLY IF the subclass methods have unique names. For example, if a subclass
    // declares a @Before method with the name bind, JUnit WILL NOT CALL the superclass method

    @BeforeClass
    static void newContainer() {
        container = EJBContainer.createEJBContainer()
    }

    @Before
    void bind() {
        container.context.bind('inject', this)
    }

    @After
    void unbind() {
        container.context.unbind('inject')
    }

    @AfterClass
    static void destroyContainer() {
        container.close()
    }
}
