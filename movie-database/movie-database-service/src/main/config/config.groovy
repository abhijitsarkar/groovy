/*
 * Copyright (c) ${date}, the original author or authors.
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

import static org.apache.lucene.util.Version.LUCENE_46

genres = [
        'Action and Adventure',
        'Animation',
        'Comedy',
        'Documentary',
        'Drama',
        'Horror',
        'R-Rated Mainstream Movies',
        'Romance',
        'Sci-Fi',
        'Thriller',
        'X-Rated'
]

includes = [
        '.avi',
        '.mkv',
        '.mp4',
        '.divx',
        '.mov'
]

luceneVersion = LUCENE_46

// This path is relative to $HOME
indexDirectoryPath = '.movie-manager/index'