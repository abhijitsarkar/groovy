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

package name.abhijitsarkar.moviemanager.web.config

import name.abhijitsarkar.moviemanager.web.MovieResource
import name.abhijitsarkar.moviemanager.web.ext.HK2Binder
import name.abhijitsarkar.moviemanager.web.ext.MoviesMarshaller

import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application

/**
 * @author Abhijit Sarkar
 */
@ApplicationPath('/')
class MovieManagerApplication extends Application {
    @Override
    Set<Class<?>> getClasses() {
        Set<Class<?>> classes = [] as Set
        classes.add(MovieResource)

        classes
    }

    @Override
    Set<Object> getSingletons() {
        Set<Object> objects = [] as Set
        objects.add(new MoviesMarshaller())
        objects.add(new HK2Binder())

        objects
    }
}
