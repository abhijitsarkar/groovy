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

package name.abhijitsarkar.moviemanager.logging

import name.abhijitsarkar.moviemanager.annotation.Logged
import name.abhijitsarkar.moviemanager.service.search.MovieSearchService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.annotation.Priority
import javax.interceptor.AroundInvoke
import javax.interceptor.Interceptor as JavaxInterceptor
import javax.interceptor.InvocationContext
import java.lang.reflect.Method

/**
 * @author Abhijit Sarkar
 */
@Logged
@JavaxInterceptor
@Priority(JavaxInterceptor.Priority.APPLICATION)
class LoggedInterceptor implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggedInterceptor)

    @AroundInvoke
    public Object logQueryAndNumHits(InvocationContext ctx) {
        Method aroundMethod = ctx.getMethod()

        String inClass = aroundMethod.declaringClass.name

        LOGGER.trace('Intercepted {}.{}.', inClass, aroundMethod.name)

        if (MovieSearchService.class.name == inClass && 'movieRips' == aroundMethod.name) {
            Object[] parameters = ctx.parameters

            LOGGER.trace('{}.{} query: {}.', inClass, aroundMethod.name, parameters[0].toString())
            LOGGER.trace('{}.{} num hits: {}.', inClass, aroundMethod.name, parameters[0].totalHits)
        }

        ctx.proceed()
    }
}