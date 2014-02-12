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

package name.abhijitsarkar.moviemanager

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.Configuration
import org.eclipse.jetty.webapp.WebAppContext
import org.eclipse.jetty.webapp.WebInfConfiguration
import org.eclipse.jetty.webapp.WebXmlConfiguration
import org.eclipse.jetty.webapp.JettyWebXmlConfiguration
import org.eclipse.jetty.annotations.AnnotationConfiguration
import org.eclipse.jetty.plus.webapp.EnvConfiguration
import org.eclipse.jetty.plus.webapp.PlusConfiguration

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.security.ProtectionDomain

/**
 * @author Abhijit Sarkar
 */
class EmbeddedJettyServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedJettyServer)

    private static final Configuration[] CONFIGURATIONS
    private static final String DEPLOYMENT_DESCRIPTOR
    private static final String[] OVERRIDE_DESCRIPTORS
    private static final String[] SERVER_CLASSES
    private static final int HTTP_PORT

    static {
        CONFIGURATIONS = [new WebInfConfiguration(), new WebXmlConfiguration(), new JettyWebXmlConfiguration(),
                new AnnotationConfiguration(), new EnvConfiguration(), new PlusConfiguration()]
        DEPLOYMENT_DESCRIPTOR = 'WEB-INF/web.xml'
        OVERRIDE_DESCRIPTORS = ['WEB-INF/jetty-override.xml'] as String[]
        HTTP_PORT = 8080
        SERVER_CLASSES = ['-org.eclipse.jetty.servlet.ServletContextHandler.Decorator'] as String[]
    }

    static void main(String[] args) {
        new EmbeddedJettyServer().start()
    }

    void start() {
        ProtectionDomain domain = EmbeddedJettyServer.protectionDomain
        URL location = domain.codeSource.location

        LOGGER.debug('Source code location - {}', location)

        WebAppContext webAppCtx = new WebAppContext()

        webAppCtx.descriptor = DEPLOYMENT_DESCRIPTOR

        // The "override" descriptors are applied on top of the web.xml. It is a good idea to separate vendor-specific
        // configuration in these.
        webAppCtx.overrideDescriptors = OVERRIDE_DESCRIPTORS

        webAppCtx.configurations = CONFIGURATIONS

        // The way Jetty's default classloading works, the web application does not see some "server" classes.
        // However, Jetty throws ClassNotFoundException: org.eclipse.jetty.servlet.ServletContextHandler$Decorator
        // during startup of the application.  Hence we call a setter method but with a '-', meant to remove
        // that class from the list of server classes.

        // http://www.eclipse.org/jetty/documentation/current/jetty-classloading.html
        webAppCtx.serverClasses = SERVER_CLASSES

        webAppCtx.war = location.toExternalForm()

        // Start the embedded server and bind it on given port
        Server server = new Server(HTTP_PORT)

        server.handler = webAppCtx

        LOGGER.debug('Starting embedded Jetty server on port {}, please wait...', HTTP_PORT)

        server.start()
        server.join()
    }
}
