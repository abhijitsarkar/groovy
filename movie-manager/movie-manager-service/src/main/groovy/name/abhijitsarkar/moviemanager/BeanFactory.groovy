package name.abhijitsarkar.moviemanager

import name.abhijitsarkar.moviemanager.domain.MovieRipFileExtension
import org.apache.lucene.store.Directory
import org.apache.lucene.store.FSDirectory
import org.apache.lucene.util.Version

/**
 * @author Abhijit Sarkar
 */
class BeanFactory {
    private final ConfigObject config

    BeanFactory(final URL url) {
        config = new ConfigSlurper().parse(url)
    }

    Collection<String> genres() {
        config.genres.asImmutable()
    }

    Collection<String> includes() {
        MovieRipFileExtension.values().collect {
            // GOTCHA ALERT: GString is not equal to String; "a" != 'a'
            ".${it.name().toLowerCase()}".toString()
        }.asImmutable()
    }

    Directory indexDirectory() {
        final File indexDirectory = new File(config.indexDirectoryPath)
        FSDirectory.open(indexDirectory)
    }

    Version version() {
        config.luceneVersion
    }
}
