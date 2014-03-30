package name.abhijitsarkar.moviedatabase.test.integration;

import groovy.lang.Binding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.support.AbstractGenericContextLoader;

import java.io.IOException;

/**
 * @author Abhijit Sarkar
 */
public class GenericGroovyContextLoader extends AbstractGenericContextLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericGroovyContextLoader.class);

    @Override
    protected BeanDefinitionReader createBeanDefinitionReader(final GenericApplicationContext context) {
        final GroovyBeanDefinitionReader reader = new GroovyBeanDefinitionReader(context);

        final Binding binding = getBinding(reader);

        reader.setBinding(binding);

        /* Don't do this - Spring will call refresh later.
         * GenericApplicationContext does not support multiple refresh attempts.
         */
        // context.refresh()

        return reader;
    }

    protected Binding getBinding(final GroovyBeanDefinitionReader reader) {
        Binding binding = reader.getBinding();

        binding = (binding == null ? new Binding() : binding);

        try {
            binding.setVariable("configFileURL", new ClassPathResource("config.groovy").getURL());
        } catch (IOException ioe) {
            LOGGER.warn("Unable to set binding variable 'configFileURL'.");
        }

        return binding;
    }

    @Override
    protected String getResourceSuffix() {
        return ".groovy";
    }
}
