@file:JvmName("ActorServerServer")
package net.microservices.tutorial.actorserverservice

import net.microservices.tutorial.actorserverservice.configurations.ActorServerConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import

/**
 * Akka server. Works as a microservice client. Uses the Discovery Server (Eureka) to find the microservice.
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(useDefaultFilters = false) // Disable component scanner
@Import(ActorServerConfiguration::class)
open class ActorServerServer {

    companion object {


        /**
         * Run the application using Spring Boot and an embedded servlet engine.

         * @param args
         * *            Program arguments - ignored.
         */
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(ActorServerServer::class.java, *args)
        }
    }

}
