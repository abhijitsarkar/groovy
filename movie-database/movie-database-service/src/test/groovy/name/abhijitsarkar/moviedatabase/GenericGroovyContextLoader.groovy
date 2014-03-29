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

import org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader
import org.springframework.beans.factory.support.BeanDefinitionReader
import org.springframework.context.support.GenericApplicationContext
import org.springframework.core.io.ClassPathResource
import org.springframework.test.context.support.AbstractGenericContextLoader

/**
 * @author Abhijit Sarkar
 */
class GenericGroovyContextLoader extends AbstractGenericContextLoader {

    @Override
    protected BeanDefinitionReader createBeanDefinitionReader(
            GenericApplicationContext context) {
        final BeanDefinitionReader reader = new GroovyBeanDefinitionReader(context)

        final Binding binding = reader.getBinding() ?: new Binding()

        binding.setVariable('configFileURL', new ClassPathResource('config.groovy').URL)

        /* The usual Groovy syntax reader.binding does not work coz binding has private visibility in the reader. */
        reader.setBinding(binding)

        /* Don't do this - Spring will call refresh later.
         * GenericApplicationContext does not support multiple refresh attempts.
         */
//        context.refresh()

        reader
    }

    @Override
    protected String getResourceSuffix() {
        return '.groovy'
    }
}
