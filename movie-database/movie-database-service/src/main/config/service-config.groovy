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

import name.abhijitsarkar.moviedatabase.service.BeanFactory
import org.springframework.core.io.ClassPathResource

/* More bean builder examples here: https://github.com/dturanski/groovy-beans,
 * here: http://spring.io/blog/2014/03/03/groovy-bean-configuration-in-spring-framework-4,
 * and here: https://github.com/welvet/spring-4-groovy
 */
beans {
    /* The configFileURL comes from the reader binding. BeanDefinitionReader is the delegate to this closure.
     * This is cool or what?
     * The variable may be simply referred to by name but if it hasn't been set, Groovy interprets it as a class
     * (i.e. this file) property and blows up. This is safer.
    */
    def defaultConfigFileURL = delegate.getBinding()?.getVariable('configFileURL') ?:
            new ClassPathResource('config.groovy').URL

    myBeanFactory(BeanFactory, defaultConfigFileURL)

    indexDirectory(myBeanFactory: 'indexDirectory')

    version(myBeanFactory: 'version')

    genres(myBeanFactory: 'genres')

    /* Static factory method. The parameter is unused, only for demo. */
    includes(BeanFactory, 'unused') { bean ->
        bean.factoryMethod = 'includes'
    }
}