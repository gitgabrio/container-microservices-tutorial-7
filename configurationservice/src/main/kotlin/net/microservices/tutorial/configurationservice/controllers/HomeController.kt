@file:JvmName("HomeController")
package net.microservices.tutorial.configurationservice.controllers

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Home page controller.

l
 */
@Controller
class HomeController {

    @Value("\${eureka.client.serviceUrl.defaultZone}")
    var defaultZone :String? = null



    @RequestMapping("/")
    fun home(model: Model): String {
        var registrarUrl = if (defaultZone != null) defaultZone?.replace("/eureka", "") else "NOT-SET"
        model.addAttribute("registrarUrl", registrarUrl)
        return "index"
    }

}
