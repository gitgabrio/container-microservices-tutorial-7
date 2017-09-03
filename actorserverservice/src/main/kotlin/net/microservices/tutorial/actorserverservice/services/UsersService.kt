@file:JvmName("UsersService")

package net.microservices.tutorial.actorserverservice.services

import net.microservices.tutorial.dto.UserDTO
import org.springframework.stereotype.Service


/**
 * Hide the access to the microservice inside this local service.

l
 */
@Service
open class UsersService {

    var users: MutableMap<Int, UserDTO> = mutableMapOf()
}
