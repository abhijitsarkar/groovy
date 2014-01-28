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

import java.util.regex.Pattern

class MovieRip extends Movie implements Comparable {
	long fileSize
	String fileExtension
	String parent

	MovieRip(movie) {
		super(movie)
	}

	@Override
	String toString() {
		def pattern = Pattern.compile('Movie*')
		super.toString().replaceAll(pattern, 'MovieRip')
	}

	@Override
	boolean equals(Object obj) {
		if (this.class != obj?.class) {
			return false
		}

		super.equals((Movie)obj)
	}

	@Override
	int hashCode() {
		// Codenarc is making me write extra code! On one hand, it thinks hashCode should've more code than just a
		// super call, on the other hand if I remove the method, Codenarc complains of it's absence.
		def hashCode = super.hashCode()

		hashCode
	}

	@Override
	int compareTo(Object o) {
		super.compareTo(o);
	}
}