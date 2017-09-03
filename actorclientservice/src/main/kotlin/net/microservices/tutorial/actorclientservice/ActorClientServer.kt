@file:JvmName("ActorClientServer")
package net.microservices.tutorial.actorclientservice

import net.microservices.tutorial.actorclientservice.configurations.ActorClientConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import

/**
 * Actor client. Works as a microservice client, talking with the Actor server.
 * Uses the Discovery Server (Eureka) to find the Actor server service.
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(useDefaultFilters = false) // Disable component scanner
@Import(ActorClientConfiguration::class)
open class ActorClientServer {

    companion object {


        /**
         * Run the application using Spring Boot and an embedded servlet engine.

         * @param args
         * *            Program arguments - ignored.
         */
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(ActorClientServer::class.java, *args)
        }
    }

}
