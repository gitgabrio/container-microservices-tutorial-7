@file:JvmName("ConfigurationConfiguration")

package net.microservices.tutorial.configurationservice.configurations

import net.microservices.tutorial.configurationservice.controllers.HomeController
import net.microservices.tutorial.configurationservice.controllers.UsersController
import net.microservices.tutorial.configurationservice.services.UsersService
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.AsyncRestTemplate
import org.springframework.web.client.RestTemplate

/**
 * Created by Gabriele Cardosi - gcardosi@cardosi.net on 04/11/2016.
 */
@Configuration
@ComponentScan("net.microservices.tutorial.configurationservice")
open class ConfigurationConfiguration {

    @Value("\${persistenceservice.url}")
    var persistenceServiceUrl :String = ""

    @Value("\${timeconsumingservice.url}")
    var timeConsumingserviceUrl :String = ""

    /**
     * A customized RestTemplate that has the ribbon load balancer build in.
     * @return
     */
    @LoadBalanced
    @Bean
    open fun restTemplate(): RestTemplate {
        return RestTemplate()
    }

    /**
     * A customized RestTemplate that has the ribbon load balancer build in.
     * @return
     */
    @LoadBalanced
    @Bean
    open fun asyncRestTemplate(): AsyncRestTemplate {
        return AsyncRestTemplate()
    }

    /**
     * The UserService encapsulates the interaction with the micro-service.

     * @return A new service instance.
     */
    @Bean
    open fun usersService(): UsersService {
        return UsersService(persistenceServiceUrl, timeConsumingserviceUrl)
    }

    /**
     * Create the controller, passing it the [UsersService] to use.

     * @return
     */
    @Bean
    open fun usersController(): UsersController {
        return UsersController(usersService())
    }

    @Bean
    open fun homeController(): HomeController {
        return HomeController()
    }

}
