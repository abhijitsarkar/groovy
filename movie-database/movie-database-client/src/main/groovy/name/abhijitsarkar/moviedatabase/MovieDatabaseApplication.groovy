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

package name.abhijitsarkar.moviedatabase

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan

/**
 * @author Abhijit Sarkar
 */

@EnableAutoConfiguration
@ComponentScan
class MovieDatabaseApplication {
    static void main(String[] args) {
        final Set<Object> sources = [MovieDatabaseApplication, 'classpath:client-config.groovy'] as Set
        final SpringApplication app = new SpringApplication()
        app.setSources(sources)

        app.run(args)
    }
}
