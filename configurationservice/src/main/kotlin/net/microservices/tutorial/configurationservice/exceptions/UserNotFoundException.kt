@file:JvmName("UserNotFoundException")
package net.microservices.tutorial.configurationservice.exceptions

/**
 * Created by Gabriele Cardosi - gcardosi@cardosi.net on 26/10/2016.
 */

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

/**
 * Allow the controller to return a 404 if an user is not found by simply
 * throwing this exception. The @ResponseStatus causes Spring MVC to return a
 * 404 instead of the usual 500.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException(userNumber: String) : RuntimeException("No such user: " + userNumber) {
    companion object {

        private val serialVersionUID = 1L
    }
}
