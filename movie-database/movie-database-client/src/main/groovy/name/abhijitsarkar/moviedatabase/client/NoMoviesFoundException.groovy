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


package name.abhijitsarkar.moviedatabase.client

/**
 * @author Abhijit Sarkar
 */
class NoMoviesFoundException extends RuntimeException {
    NoMoviesFoundException() {
    }

    NoMoviesFoundException(String s) {
        super(s)
    }

    NoMoviesFoundException(String s, Throwable throwable) {
        super(s, throwable)
    }

    NoMoviesFoundException(Throwable throwable) {
        super(throwable)
    }

    NoMoviesFoundException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1)
    }
}
