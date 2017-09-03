@file:JvmName("HomeController")
package net.microservices.tutorial.actorserverservice.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Home page controller.

 *
 */
@Controller
class HomeController {

    @RequestMapping("/")
    fun home(): String {
        return "index"
    }

}
