@file:JvmName("RegistrationServer")
package net.microservices.tutorial.registrationservice

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@EnableAutoConfiguration(exclude = arrayOf(DataSourceAutoConfiguration::class, DataSourceTransactionManagerAutoConfiguration::class, HibernateJpaAutoConfiguration::class))
@ComponentScan
@EnableEurekaServer
open class RegistrationServer {

    companion object {

            /**
             * Run the application using Spring Boot and an embedded servlet engine.

             * @param args
             * *            Program arguments - ignored.
             */
            @JvmStatic fun main(args: Array<String>) {
                SpringApplication.run(RegistrationServer::class.java, *args)
            }
        }
}
