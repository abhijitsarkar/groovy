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

import name.abhijitsarkar.moviedatabase.service.index.IndexField
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

import javax.annotation.Resource
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

/**
 * @author Abhijit Sarkar
 */
@Component
class ValidIndexFieldValidation implements ConstraintValidator<ValidIndexField, String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidIndexFieldValidation)

    final Collection<String> indexFields = IndexField.values().collect {
        it.name()
    }

    @Override
    void initialize(final ValidIndexField constraintAnnotation) {

    }

    @Override
    boolean isValid(final String indexField, final ConstraintValidatorContext context) {
        if (isNotAnIndexField(indexField)) {
            final String message = "${indexField} is not a valid index field. " +
                    "Valid fields are ${indexFields.join(', ')}."

            LOGGER.error(message)


            context.disableDefaultConstraintViolation()
            newConstraintViolation(context, message)

            return false
        }

        true
    }

    private boolean isNotAnIndexField(final String indexField) {
        !(indexField?.toUpperCase() in indexFields)
    }

    // c.f. http://beanvalidation.org/1.1/spec
    private void newConstraintViolation(final ConstraintValidatorContext context, final String message) {
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation()
    }
}
