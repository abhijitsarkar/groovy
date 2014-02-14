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

import name.abhijitsarkar.moviemanager.facade.MovieFacade

import javax.enterprise.context.spi.CreationalContext
import javax.enterprise.inject.spi.Bean
import javax.enterprise.inject.spi.BeanManager
import javax.enterprise.inject.spi.CDI

import org.glassfish.hk2.utilities.binding.AbstractBinder

/**
 * @author Abhijit Sarkar
 */
class HK2Binder extends AbstractBinder {
    @Override
    protected void configure() {
        BeanManager bm = getBeanManager()
        bind(getBean(bm, MovieFacade)).to(MovieFacade)
    }

    private BeanManager getBeanManager() {
        CDI.current().getBeanManager()
    }

    private <T> T getBean(BeanManager bm, Class<T> clazz) {
        Bean<T> bean = (Bean<T>) bm.getBeans(clazz).iterator().next()
        CreationalContext<T> ctx = bm.createCreationalContext(bean)
        return (T) bm.getReference(bean, clazz, ctx)
    }
}
