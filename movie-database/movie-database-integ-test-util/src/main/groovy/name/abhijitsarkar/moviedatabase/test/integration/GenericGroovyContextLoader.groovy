package name.abhijitsarkar.moviedatabase.test.integration

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader
import org.springframework.beans.factory.support.BeanDefinitionReader
import org.springframework.context.support.GenericApplicationContext
import org.springframework.core.io.ClassPathResource
import org.springframework.test.context.support.AbstractGenericContextLoader

/**
 * @author Abhijit Sarkar
 */
public class GenericGroovyContextLoader extends AbstractGenericContextLoader {
    @Override
    protected BeanDefinitionReader createBeanDefinitionReader(GenericApplicationContext context) {
        final BeanDefinitionReader reader = new GroovyBeanDefinitionReader(context)

        final Binding binding = reader.getBinding() ?: new Binding()

        binding.setVariable('configFileURL', new ClassPathResource('config.groovy').URL)

        reader.setBinding(binding)

        /* Don't do this - Spring will call refresh later.
         * GenericApplicationContext does not support multiple refresh attempts.
         */
//        context.refresh()

        return reader
    }

    @Override
    protected String getResourceSuffix() {
        return '.groovy'
    }
}
