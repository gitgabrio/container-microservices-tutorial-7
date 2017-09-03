@file:JvmName("TimeConsumingServer")
package net.microservices.tutorial.timeconsumingservice

import net.microservices.tutorial.timeconsumingservice.configurations.TimeConsumingConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import

/**
 * Users web-server. Works as a microservice client, creating fake users on-demand. Uses the Discovery Server (Eureka) to find the microservice.
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(useDefaultFilters = false) // Disable component scanner
@Import(TimeConsumingConfiguration::class)
open class TimeConsumingServer {

    companion object {


        /**
         * Run the application using Spring Boot and an embedded servlet engine.

         * @param args
         * *            Program arguments - ignored.
         */
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(TimeConsumingServer::class.java, *args)
        }
    }

}
