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

class Movie implements Comparable {
	protected String title
	protected List<String> genres
	protected Date releaseDate
	protected CastAndCrew director
	protected List<CastAndCrew> stars
	protected float imdbRating
	protected URL imdbURL

	Movie() {
	}

	Movie(anotherMovie) {
		title = anotherMovie.title
		genres = anotherMovie.genres
		releaseDate = anotherMovie.releaseDate
		director = anotherMovie.director
		stars = anotherMovie.stars
		imdbRating = anotherMovie.imdbRating
		imdbURL = anotherMovie.imdbURL
	}

	@Override
	String toString() {
		"Movie[title:${title}, year:${releaseDate[Calendar.YEAR]}, genres:${genres}]"
	}

	@Override
	boolean equals(Object obj) {
		if (!this.class.isAssignableFrom(obj?.class)) {
			return false
		}

		compareTo(obj) == 0
	}

	@Override
	int hashCode() {
		def result = 17
		def c = 0
		def magicNum = 37

		c = title ? title.hashCode() : 0
		result = magicNum * result + c

		c = this.releaseDate[Calendar.YEAR] ?: 0
		result = magicNum * result + c

		// Use the Spread operator to sum all hash codes
		c = genres ? genres*.hashCode().sum() : 0
		result = magicNum * result + c
	}

	@Override
	int compareTo(Object o) {
		final int EQUAL = 0;
		
		if (!this.class.isAssignableFrom(o?.class)) {
			throw new IllegalArgumentException("Invalid type parameter: ${o?.class.name}")
		}

		if (title == o.title) {
			def releaseDateDiff = (releaseDate[Calendar.YEAR] ?: 0) - (o.releaseDate[Calendar.YEAR] ?: 0)

			if (releaseDateDiff == 0) {
				def genreSizeDiff = (genres?.size() ?: 0) - (o.genres?.size() ?: 0)

				if (genreSizeDiff == 0) {
					if (genres?.containsAll(o.genres as String[])) {
						return 0
					} else {
						return 1
					}
				} else {
					genreSizeDiff
				}
			} else {
				releaseDateDiff
			}
		} else {
			title?.hashCode() - o.title?.hashCode()
		}
	}
}