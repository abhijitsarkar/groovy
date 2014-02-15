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

package name.abhijitsarkar.moviemanager.annotation

import name.abhijitsarkar.moviemanager.validation.ValidDirectoryValidation

import javax.validation.Constraint
import javax.validation.Payload
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 * @author Abhijit Sarkar
 */
@Constraint(validatedBy = [ValidDirectoryValidation])
@Target([ElementType.METHOD,
ElementType.FIELD,
ElementType.ANNOTATION_TYPE,
ElementType.CONSTRUCTOR,
ElementType.PARAMETER])
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDirectory {
    String message() default '{name.abhijitsarkar.moviemanager.annotation.ValidDirectory.message}'

    Class<?>[] groups() default []

    Class<? extends Payload>[] payload() default []
}