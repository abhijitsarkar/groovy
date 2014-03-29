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

/**
 * @author Abhijit Sarkar
 */

package name.abhijitsarkar.moviemanager.domain
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(callSuper = true, excludes = ['fileSize', 'fileExtension', 'parent'])
class MovieRip extends Movie implements Comparable {
    long fileSize
    String fileExtension
    String parent

    MovieRip(final Movie movie) {
        super(movie)
    }

//    // The getters and setters are required for serialization
//
//    long getFileSize() {
//        fileSize
//    }
//
//    void setFileSize(long fileSize) {
//        this.fileSize = fileSize
//    }
//
//    String getFileExtension() {
//        fileExtension
//    }
//
//    void setFileExtension(String fileExtension) {
//        this.fileExtension = fileExtension
//    }
//
//    String getParent() {
//        parent
//    }
//
//    void setParent(String parent) {
//        this.parent = parent
//    }

//    @Override
//    boolean equals(Object obj) {
//        if (this.class != obj?.class) {
//            return false
//        }
//
//        super.equals((Movie) obj)
//    }
//
//    @Override
//    int hashCode() {
//        // Codenarc is making me write extra code! On one hand, it thinks hashCode should've more code than just a
//        // super call, on the other hand if I remove the method, Codenarc complains of it's absence.
//        int hashCode = super.hashCode()
//
//        hashCode
//    }

    @Override
    String toString() {
       super.toString()
    }

    @Override
    int compareTo(Object o) {
        super.compareTo(o)
    }
}