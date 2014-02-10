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
    private String title
    private Set<String> genres
    private Date releaseDate
    private CastAndCrew director
    private Set<CastAndCrew> stars
    private float imdbRating
    private URL imdbURL

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

    // The getters and setters are required for serialization

    String getTitle() {
        title
    }

    void setTitle(String title) {
        this.title = title
    }

    Set<String> getGenres() {
        genres
    }

    void setGenres(Set<String> genres) {
        this.genres = genres
    }

    Date getReleaseDate() {
        releaseDate
    }

    void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate
    }

    CastAndCrew getDirector() {
        director
    }

    void setDirector(CastAndCrew director) {
        this.director = director
    }

    Set<CastAndCrew> getStars() {
        stars
    }

    void setStars(Set<CastAndCrew> stars) {
        this.stars = stars
    }

    float getImdbRating() {
        imdbRating
    }

    void setImdbRating(float imdbRating) {
        this.imdbRating = imdbRating
    }

    URL getImdbURL() {
        imdbURL
    }

    void setImdbURL(URL imdbURL) {
        this.imdbURL = imdbURL
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

        (this <=> obj) == 0
    }

    @Override
    int hashCode() {
        int result = 17
        int c = 0
        int magicNum = 37

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
        final int EQUAL = 0
        final int GREATER = 1

        if (!this.class.isAssignableFrom(o?.class)) {
            throw new IllegalArgumentException("Invalid type parameter: ${o?.class.name}")
        }

        if (title == o.title) {
            int releaseDateDiff = (releaseDate[Calendar.YEAR] ?: 0) - (o.releaseDate[Calendar.YEAR] ?: 0)

            if (releaseDateDiff == 0) {
                int genreSizeDiff = (genres?.size() ?: 0) - (o.genres?.size() ?: 0)

                if (genreSizeDiff == 0) {
                    return genres?.containsAll((String[]) o.genres) ? EQUAL : GREATER
                }
                return genreSizeDiff
            }
            return releaseDateDiff
        }
        title?.hashCode() - o.title?.hashCode()
    }
}