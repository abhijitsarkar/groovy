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


package name.abhijitsarkar.moviedatabase.service.validation

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

/**
 * @author Abhijit Sarkar
 */
class ValidDirectoryValidation implements ConstraintValidator<ValidDirectory, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidDirectoryValidation)

    @Override
    void initialize(ValidDirectory constraintAnnotation) {

    }

    @Override
    boolean isValid(String directory, ConstraintValidatorContext context) {
        String message

        if (isNull(directory)) {
            message = "${directory} is null."
        } else if (doesNotExist(directory)) {
            message = "${directory} does not exist or is not a directory."
        } else if (cannotRead(directory)) {
            message = "${directory} does not exist or is not readable."
        }

        if (message) {
            LOGGER.error(message)

            context.disableDefaultConstraintViolation()
            newConstraintViolation(context, message)

            return false
        }

        true
    }

    private boolean isNull(String directory) {
        !directory
    }

    private boolean doesNotExist(String directory) {
        final File rootDir = new File(directory)

        !rootDir.exists() || !rootDir.isDirectory()
    }

    private boolean cannotRead(String directory) {
        final File rootDir = new File(directory)

        !rootDir.canRead()
    }

    // c.f. http://beanvalidation.org/1.1/spec
    private void newConstraintViolation(ConstraintValidatorContext context, String message) {
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation()
    }
}
