package net.microservices.tutorial.messages

import java.io.Serializable

/**
 * Created by Gabriele Cardosi - gcardosi@cardosi.net on 30/07/17.
 */
open class AkkaResponse(val done: Boolean, val id: Int): Serializable {
    override fun toString(): String{
        return "AkkaResponse(done=$done, id=$id)"
    }
}
