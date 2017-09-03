@file:JvmName("HomeController")
package net.microservices.tutorial.akkaclusterservice.controllers

import net.microservices.tutorial.akkaclusterservice.services.HomeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Home page controller.

 *
 */
@Controller
class HomeController {

    @Autowired
    var homeService: HomeService? = null

    @RequestMapping("/")
    fun home(model: Model): String {
        val heapMemory = homeService?.heapMemory ?: "UNKNOWN"
        val processors = homeService?.processors ?: "UNKNOWN"
        val loadAverage = homeService?.loadAverage ?: "UNKNOWN"
        val nodes = homeService?.nodeStatusMap ?: mutableMapOf()
        model.addAttribute("heapMemory", heapMemory)
        model.addAttribute("processors", processors)
        model.addAttribute("loadAverage", loadAverage)
        model.addAttribute("nodes", nodes)
        return "index"
    }

}
