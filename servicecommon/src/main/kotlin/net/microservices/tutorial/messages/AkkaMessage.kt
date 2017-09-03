package net.microservices.tutorial.messages

import net.microservices.tutorial.commands.Command
import net.microservices.tutorial.dto.UserDTO
import java.io.Serializable

/**
 * Created by Gabriele Cardosi - gcardosi@cardosi.net on 30/07/17.
 */
open class AkkaMessage(val user: UserDTO, val command: Command, val id: Int) : Serializable {

    override fun toString(): String{
        return "AkkaMessage(command=$command, user=$user, id=$id)"
    }
}