package net.microservices.tutorial.classes

import net.microservices.tutorial.dto.UserDTO
import java.lang.StringBuilder
import java.util.*

/**
 * Created by Gabriele Cardosi - gcardosi@cardosi.net on 27/07/17.
 */

/**
 * Helper functions that returns </code>true</true> if
 * all the given arguments are <b>not null</b>. It return
 * <code>false</code> if at least one element is <code>null</code>
 * <b>OR</b> if no arguments is given at all
 *
 */
fun notNull(vararg any: Any?): Boolean {
    if (any.size == 0) {
        return false
    }
    for (a in any) {
        if (a == null) {
            return false
        }
    }
    return true
}

/**
 * Helper function that return a newly-generated {@code UserDTO} with the given
 * <b>id</b>
 */
fun createUserDTO(id: Int): UserDTO {
    val toReturn = UserDTO()
    toReturn.id = id
    toReturn.name = getSaltString (Random().nextInt(7) + 3)
    toReturn.surname = getSaltString(Random().nextInt(7) + 3)
    return toReturn

}

private fun getSaltString(size: Int): String {
    val SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val salt = StringBuilder()
    val rnd = Random()
    while (salt.length < size) { // length of the random string.
        val index = rnd.nextInt(SALTCHARS.length)
        salt.append(SALTCHARS[index])
    }
    val saltStr = salt.toString()
    return saltStr
}
