import name.abhijitsarkar.moviemanager.BeanFactory
import org.springframework.core.io.ClassPathResource

beans {
    def configFileURL = new ClassPathResource('config.groovy').URL

    myBeanFactory(BeanFactory, configFileURL)

    indexDirectory(myBeanFactory: 'indexDirectory')

    version(myBeanFactory: 'version')

    genres(myBeanFactory: 'genres')

    includes(myBeanFactory: 'includes')
}