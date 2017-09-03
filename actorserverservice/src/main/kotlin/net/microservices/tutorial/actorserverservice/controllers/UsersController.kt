@file:JvmName("UsersController")

package net.microservices.tutorial.actorserverservice.controllers

import net.microservices.tutorial.actorserverservice.services.UsersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.RequestMapping
import java.util.logging.Logger

/**
 * Client controller, fetches User info from the microservice via
 * [UsersService].
l
 */
@Controller
class UsersController(

        @Autowired
        protected var usersService: UsersService) {

    protected var logger = Logger.getLogger(UsersController::class.java.name)

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.setAllowedFields("userNumber", "surname", "name")
    }

    @RequestMapping("/users")
    fun allUsers(model: Model): String {
        logger.info("UsersController allUsers() invoked")
        val users = usersService.users.values
        logger.info("UsersController allUsers() found: " + users)
        model.addAttribute("users", users)
        return "users"
    }

}
