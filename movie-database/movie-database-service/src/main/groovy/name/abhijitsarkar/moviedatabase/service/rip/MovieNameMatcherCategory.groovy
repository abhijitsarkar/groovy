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


package name.abhijitsarkar.moviedatabase.service.rip

import java.util.regex.Matcher
import java.util.regex.Pattern

import static name.abhijitsarkar.moviedatabase.service.rip.MovieRipParser.fileExtension

/**
 * @author Abhijit Sarkar
 */
@Category(Matcher)
class MovieNameMatcherCategory {
    /*
     * The following regex matches file names with release year in parentheses,
     * something like Titanic (1997).mkv Each part of the regex is explained
     * further:
     *
     * ([-',!\\[\\]\\.\\w\\s]++) -> Matches one or more occurrences of any
     * alphabet, number or the following special characters in the movie name:
     * dash (-), apostrophe ('), comma (,), exclamation sign (!), square braces
     * ([]), full stop (.)
     *
     * (?:\\((\\d{4})\\)) -> Matches 4 digit release year within parentheses.
     *
     * (.++) -> Matches one or more occurrences of any character.
     */
    static final Pattern MOVIE_NAME_WITH_RELEASE_YEAR_REGEX =
            ~/(?<title>[-',!\[\]\.\w\s]++)(?:\((?<year>\d{4})\))?+(?<lastPart>.++)/

    String getTitle() {
        final String fileExtension = fileExtension(this.group())
        final String qualifier = getQualifier(fileExtension)
        String title = this.group('title')

        title += (qualifier == fileExtension ? '' : qualifier)

        title.trim()
    }

    int getYear() {
        final String group2 = this.group('year')

        group2 ? Integer.parseInt(group2) : 0
    }

    String getQualifier(final String fileExtension) {
        this.group('lastPart').minus(fileExtension)
    }
}
