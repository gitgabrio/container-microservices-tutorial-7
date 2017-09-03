package net.microservices.tutorial.configurationservice.configurations

import net.microservices.tutorial.configurationservice.AbstractConfigurationServiceTest
import net.microservices.tutorial.configurationservice.controllers.HomeController
import net.microservices.tutorial.configurationservice.controllers.UsersController
import net.microservices.tutorial.configurationservice.services.UsersService
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.client.AsyncRestTemplate
import org.springframework.web.client.RestTemplate

/**
 * Created by Gabriele Cardosi - gcardosi@cardosi.net on 29/05/17.
 */
class ConfigurationConfigurationTest : AbstractConfigurationServiceTest() {


    @Autowired
    var restTemplate: RestTemplate? = null

    @Autowired
    var asyncRestTemplate: AsyncRestTemplate? = null

    @Autowired
    var homeController: HomeController? = null

    @Autowired
    var usersService: UsersService? = null

    @Autowired
    var usersController: UsersController? = null

    @Test
    fun testRestTemplate() {
        assertNotNull(restTemplate)
    }

    @Test
    fun testAsyncRestTemplate() {
        assertNotNull(asyncRestTemplate)
    }

    @Test
    fun testUsersService() {
        assertNotNull(usersService)
    }

    @Test
    fun testUsersController() {
        assertNotNull(usersController)
    }

    @Test
    fun testHomeController() {
        assertNotNull(homeController)
    }
}