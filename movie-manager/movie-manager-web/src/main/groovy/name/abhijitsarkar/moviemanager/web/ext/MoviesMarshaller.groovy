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

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import name.abhijitsarkar.moviemanager.domain.MovieRip

import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.ext.MessageBodyWriter
import javax.ws.rs.ext.Provider
import java.lang.annotation.Annotation
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * @author Abhijit Sarkar
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
class MoviesMarshaller implements MessageBodyWriter<Set<MovieRip>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MoviesMarshaller)

    @Override
    boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        if (!(genericType instanceof ParameterizedType)) {
            LOGGER.trace('Generic type not a ParameterizedType but {}.', genericType)

            return false
        }

        ParameterizedType pType = (ParameterizedType) genericType

        Type t = pType.actualTypeArguments[0]

        if (!t) {
            LOGGER.trace('Either Parameterized type does not contain any actual type args or the later is null.')

            return false
        }

        if (!MediaType.APPLICATION_JSON_TYPE == mediaType) {
            LOGGER.trace('Media type is not JSON but {}.', mediaType)

            return false
        }

        LOGGER.trace('Type {}, actual type args {}.', type, t)

        Set.isAssignableFrom(type) && t == MovieRip
    }

    @Override
    long getSize(Set<MovieRip> movieRips, Class<?> type, Type genericType,
                 Annotation[] annotations, MediaType mediaType) {
        -1
    }

    @Override
    void writeTo(Set<MovieRip> movieRips, Class<?> type, Type genericType,
                 Annotation[] annotations, MediaType mediaType,
                 MultivaluedMap<String, Object> httpHeaders,
                 OutputStream entityStream) {
        LOGGER.debug('Writing movie rips {} to output stream.', movieRips.toString())

        writeAsJson(movieRips, entityStream)
    }

    void writeAsJson(Set<MovieRip> movieRips, OutputStream entityStream) {
        ObjectMapper mapper = new ObjectMapper()
        mapper.dateFormat = readableDateFormat()

        mapper.writerWithType(new TypeReference<Set<MovieRip>>() {
        } ).writeValue(entityStream, movieRips)
    }

    DateFormat readableDateFormat() {
        final String dateFormat = 'yyyy'

        DateFormat df = DateFormat.dateInstance

        if (df instanceof SimpleDateFormat) {
            ((SimpleDateFormat) df).applyPattern(dateFormat)
        }

        df
    }
}
