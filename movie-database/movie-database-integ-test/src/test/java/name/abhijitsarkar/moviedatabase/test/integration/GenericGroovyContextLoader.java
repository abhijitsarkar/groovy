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

package name.abhijitsarkar.moviedatabase.test.integration;

import groovy.lang.Binding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.support.AbstractGenericContextLoader;

import java.io.IOException;

/**
 * @author Abhijit Sarkar
 */
public class GenericGroovyContextLoader extends AbstractGenericContextLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericGroovyContextLoader.class);

    @Override
    protected BeanDefinitionReader createBeanDefinitionReader(final GenericApplicationContext context) {
        final GroovyBeanDefinitionReader reader = new GroovyBeanDefinitionReader(context);

        final Binding binding = getBinding(reader);

        reader.setBinding(binding);

        /* Don't do this - Spring will call refresh later.
         * GenericApplicationContext does not support multiple refresh attempts.
         */
        // context.refresh()

        return reader;
    }

    protected Binding getBinding(final GroovyBeanDefinitionReader reader) {
        Binding binding = reader.getBinding();

        binding = (binding == null ? new Binding() : binding);

        try {
            binding.setVariable("configFileURL", new ClassPathResource("config.groovy").getURL());
        } catch (IOException ioe) {
            LOGGER.warn("Unable to set binding variable 'configFileURL'.");
        }

        return binding;
    }

    @Override
    protected String getResourceSuffix() {
        return ".groovy";
    }
}
