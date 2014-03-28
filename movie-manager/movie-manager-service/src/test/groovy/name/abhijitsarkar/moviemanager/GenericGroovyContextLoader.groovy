package name.abhijitsarkar.moviemanager

import org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader
import org.springframework.beans.factory.support.BeanDefinitionReader
import org.springframework.context.support.GenericApplicationContext
import org.springframework.test.context.support.AbstractGenericContextLoader

/**
 * @author Abhijit Sarkar
 */
class GenericGroovyContextLoader extends AbstractGenericContextLoader {

    @Override
    protected BeanDefinitionReader createBeanDefinitionReader(
            GenericApplicationContext context) {
        new GroovyBeanDefinitionReader(context)
    }

    @Override
    protected String getResourceSuffix() {
        return '.groovy'
    }
}
