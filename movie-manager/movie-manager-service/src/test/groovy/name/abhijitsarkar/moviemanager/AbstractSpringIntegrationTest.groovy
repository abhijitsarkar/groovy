package name.abhijitsarkar.moviemanager

import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * @author Abhijit Sarkar
 */
@RunWith(SpringJUnit4ClassRunner)
@ContextConfiguration(locations = ['classpath:service-config.groovy', 'classpath:service-config-test.groovy',
        'classpath:service-config-scan.groovy'], loader = GenericGroovyContextLoader)
abstract class AbstractSpringIntegrationTest {
//    @BeforeClass
//    static void oneTimeSetUp() {
//        GenericGroovyApplicationContext context = new GenericGroovyApplicationContext()
//
//        Binding binding = new Binding()
//        binding.setVariable('beanFactory', TestBeanFactory)
//        context.getReader().setBinding(binding)
//        context.load('classpath:service-config.groovy')
//
//        context.refresh()
//    }
}
