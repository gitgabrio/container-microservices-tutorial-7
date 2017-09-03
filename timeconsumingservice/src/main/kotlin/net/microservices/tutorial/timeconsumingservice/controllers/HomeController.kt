@file:JvmName("TimeConsumingController")
package net.microservices.tutorial.timeconsumingservice.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Home page controller.
 *
 */
@Controller
class HomeController {


    @RequestMapping("/")
    fun home(model: Model): String {
        return "index"
    }

}
