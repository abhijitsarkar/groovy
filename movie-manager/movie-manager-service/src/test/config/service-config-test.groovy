import name.abhijitsarkar.moviemanager.TestBeanFactory

beans {
    myTestBeanFactory(TestBeanFactory)

    indexDirectory(myTestBeanFactory: 'indexDirectory')
}