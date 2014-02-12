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

package name.abhijitsarkar.moviemanager.web.ext

import javax.enterprise.inject.spi.BeanManager
import javax.naming.InitialContext

import org.hibernate.validator.internal.cdi.InjectingConstraintValidatorFactory
import org.hibernate.validator.internal.cdi.DestructibleBeanInstance

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorFactory

/**
 * @author Abhijit Sarkar
 */
class CDIConstraintValidatorFactory implements ConstraintValidatorFactory {
    private final BeanManager beanManager
    private final Map<Object, DestructibleBeanInstance<?>> constraintValidatorMap =
            Collections.synchronizedMap( new IdentityHashMap<Object, DestructibleBeanInstance<?>>() )

    CDIConstraintValidatorFactory() {
        String name = 'java:comp/env/' + BeanManager.class.getSimpleName()
        InitialContext ic = new InitialContext()
        beanManager = (BeanManager) ic.lookup(name)
    }

    @Override
    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
        DestructibleBeanInstance<T> destructibleBeanInstance = new DestructibleBeanInstance<T>(beanManager, key)
        constraintValidatorMap.put(destructibleBeanInstance.getInstance(), destructibleBeanInstance)
        return destructibleBeanInstance.getInstance()
    }

    @Override
    public void releaseInstance(ConstraintValidator<?, ?> instance) {
        DestructibleBeanInstance<?> destructibleBeanInstance = constraintValidatorMap.remove(instance)
        destructibleBeanInstance.destroy()
    }
}
