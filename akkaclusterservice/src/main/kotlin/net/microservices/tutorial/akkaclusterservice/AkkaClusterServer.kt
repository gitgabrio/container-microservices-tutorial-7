@file:JvmName("AkkaClusterServer")
package net.microservices.tutorial.akkaclusterservice

import net.microservices.tutorial.akkaclusterservice.configurations.AkkaClusterConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import

/**
 * Akka Cluster server. Works as a microservice client. Uses the Discovery Server (Eureka) to find the microservice.
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(useDefaultFilters = false) // Disable component scanner
@Import(AkkaClusterConfiguration::class)
open class AkkaClusterServer {

    companion object {


        /**
         * Run the application using Spring Boot and an embedded servlet engine.

         * @param args
         * *            Program arguments - ignored.
         */
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(AkkaClusterServer::class.java, *args)
        }
    }

}
