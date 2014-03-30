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

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Class-level annotation that is used to determine how to load and configure an
 * ApplicationContext for integration tests. Similar to the standard
 * {@link org.springframework.test.context.ContextConfiguration} but uses
 * {@link GenericGroovyContextLoader}.
 *
 * @author Abhijit Sarkar
 * @see GenericGroovyContextLoader
 */
@ContextConfiguration(loader = GenericGroovyContextLoader.class)
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface GroovyContextConfiguration {

    /**
     * @see ContextConfiguration#locations()
     */
    String[] locations() default {};

    /**
     * @see ContextConfiguration#classes()
     */
    Class<?>[] classes() default {};

    /**
     * @see ContextConfiguration#initializers()
     */
    Class<? extends ApplicationContextInitializer<? extends ConfigurableApplicationContext>>[] initializers() default {};

    /**
     * @see ContextConfiguration#inheritLocations()
     */
    boolean inheritLocations() default true;

    /**
     * @see ContextConfiguration#inheritInitializers()
     */
    boolean inheritInitializers() default true;

    /**
     * @see ContextConfiguration#name()
     */
    String name() default "";

}

