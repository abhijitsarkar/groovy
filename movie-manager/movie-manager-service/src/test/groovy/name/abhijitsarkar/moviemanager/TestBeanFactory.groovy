package name.abhijitsarkar.moviemanager

import org.apache.lucene.store.Directory
import org.apache.lucene.store.RAMDirectory

/**
 * @author Abhijit Sarkar
 */
class TestBeanFactory {
    Directory indexDirectory() {
        Directory indexDir = new RAMDirectory()
    }
}
