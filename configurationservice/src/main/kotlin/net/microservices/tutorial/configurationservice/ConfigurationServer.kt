@file:JvmName("ConfigurationServer")
package net.microservices.tutorial.configurationservice

import net.microservices.tutorial.configurationservice.configurations.ConfigurationConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import

/**
 * Users web-server. Works as a microservice client, fetching data from the
 * Persistence and TimeConsuming services. Uses the Discovery Server (Eureka) to find the microservice.
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(useDefaultFilters = false) // Disable component scanner
@Import(ConfigurationConfiguration::class)
open class ConfigurationServer {

    companion object {


        /**
         * Run the application using Spring Boot and an embedded servlet engine.

         * @param args
         * *            Program arguments - ignored.
         */
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(ConfigurationServer::class.java, *args)
        }
    }
}
