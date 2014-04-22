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

import name.abhijitsarkar.moviedatabase.service.search.QueryBuilder
import org.junit.Test

import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.ValidatorFactory
import javax.validation.executable.ExecutableValidator
import java.lang.annotation.Annotation
import java.lang.reflect.Method

/**
 * @author Abhijit Sarkar
 */
class ValidIndexFieldTest {
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory()
    private ExecutableValidator executableValidator = factory.validator.forExecutables()

    private QueryBuilder queryBuilder = new QueryBuilder()

    @Test
    public void testInvalidIndexField() {
        Method searchByField = QueryBuilder.getMethod('perFieldQuery', String, String)
        Object[] args = ['whatever', 'whatever'] as String[]

        Set<ConstraintViolation<QueryBuilder>> violations = executableValidator.validateParameters(
                queryBuilder, searchByField, args
        )

        assertViolations(violations, 1)
    }

    @Test
    public void testNullIndexField() {
        Method searchByField = QueryBuilder.getMethod('perFieldQuery', String, String)
        Object[] args = ['whatever', null] as String[]

        Set<ConstraintViolation<QueryBuilder>> violations = executableValidator.validateParameters(
                queryBuilder, searchByField, args
        )

        assertViolations(violations, 1)
    }

    @Test
    public void testValidIndexField() {
        Method searchByField = QueryBuilder.getMethod('perFieldQuery', String, String)
        Object[] args = ['whatever', 'title'] as String[]

        Set<ConstraintViolation<QueryBuilder>> violations = executableValidator.validateParameters(
                queryBuilder, searchByField, args
        )

        assertNoViolations(violations)
    }

    private void assertViolations(Set<ConstraintViolation<QueryBuilder>> violations, int numViolations) {
        assert numViolations == violations.size()

        Class<? extends Annotation> constraintType = null

        violations.each { ConstraintViolation<QueryBuilder> aViolation ->
            constraintType = aViolation.constraintDescriptor.annotation.annotationType()

            assert ValidIndexField == constraintType
        }
    }

    private void assertNoViolations(Set<ConstraintViolation<QueryBuilder>> violations) {
        assert !violations
    }
}
