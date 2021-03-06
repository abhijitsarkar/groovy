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


package name.abhijitsarkar.moviedatabase.service


import org.apache.lucene.store.Directory
import org.apache.lucene.store.FSDirectory
import org.apache.lucene.util.Version

/**
 * @author Abhijit Sarkar
 */
class BeanFactory {
    private static ConfigObject config

    BeanFactory(final URL url) {
        config = new ConfigSlurper().parse(url)
    }

    Collection<String> genres() {
        config.genres.asImmutable()
    }

    Directory indexDirectory() {
        String userHome = System.properties['user.home']
        File indexDirectory = new File(userHome, config.indexDirectoryPath)

        FSDirectory.open(indexDirectory)
    }

    Version version() {
        config.luceneVersion
    }

    static Collection<String> includes(String unused) {
        config.includes.asImmutable()
    }
}
