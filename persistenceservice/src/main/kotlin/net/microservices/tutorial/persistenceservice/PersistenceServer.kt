@file:JvmName("PersistenceServer")
package net.microservices.tutorial.persistenceservice


import net.microservices.tutorial.persistenceservice.configurations.PersistenceConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Import

import java.util.logging.Logger

/**
 * Run as a micro-service, registering with the Discovery Server (Eureka).
 *
 *
 * Note that the configuration for this application is imported from
 * [PersistenceConfiguration]. This is a deliberate separation of concerns.
 */
@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(PersistenceConfiguration::class)
open class PersistenceServer {

    protected var logger = Logger.getLogger(PersistenceServer::class.java.name)

    companion object {

        /**
         * Run the application using Spring Boot and an embedded servlet engine.

         * @param args
         * *            Program arguments - ignored.
         */
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(PersistenceServer::class.java, *args)
        }
    }
}
