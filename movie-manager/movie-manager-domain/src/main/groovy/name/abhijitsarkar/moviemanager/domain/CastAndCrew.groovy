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

/**
 * @author Abhijit Sarkar
 */

package name.abhijitsarkar.moviemanager.domain

class CastAndCrew {
    String name

    CastAndCrew(name) {
        this.name = name
    }

    @Override
    String toString() {
        "${this.class.simpleName}[name:${name}]"
    }

    @Override
    int hashCode() {
        int result = 17
        int c = 0
        int magicNum = 37

        c = name ? name.hashCode() : 0
        result = magicNum * result + c
    }

    @Override
    boolean equals(Object obj) {
        if (this.class != obj?.class) {
            return false
        }

        this.name == obj?.name
    }
}