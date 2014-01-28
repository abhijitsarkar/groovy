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

package name.abhijitsarkar.moviemanager.domain

import javax.ejb.embeddable.EJBContainer

import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass

abstract class AbstractCDITest {
	protected static EJBContainer container;

	// GOTCHA ALERT: The following methods are guaranteed to be called before the corresponding ones in the subclasses
	// but ONLY IF the subclass methods have unique names. For example, if a subclass
	// declares a @Before method with the name bind, JUnit WILL NOT CALL the superclass method

	@BeforeClass
	public static void createContainer() {
		container = EJBContainer.createEJBContainer()
	}

	@Before
	public void bind() {
		container.getContext().bind('inject', this)
	}

	@After
	public void unbind() {
		container.getContext().unbind('inject')
	}

	@AfterClass
	public static void destroyContainer() {
		container.close()
	}
}