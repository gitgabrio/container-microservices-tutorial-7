@file:JvmName("TimeConsumingService")

package net.microservices.tutorial.timeconsumingservice.services

import net.microservices.tutorial.classes.createUserDTO
import net.microservices.tutorial.dto.UserDTO
import org.springframework.stereotype.Service
import java.lang.Exception
import java.lang.StringBuilder
import java.util.*
import java.util.logging.Logger

/**
 * Hide the access to the microservice inside this local service.
 *
 */
@Service
class TimeConsumingService {


    protected var logger = Logger.getLogger(TimeConsumingService::class.java.name)


    @Throws(Exception::class)
    fun findAll(): List<UserDTO>? {
        logger.info("findAll() invoked")
        val toReturn: ArrayList<UserDTO> = ArrayList()
        repeat(6) { i ->
            val toAdd: UserDTO = createUserDTO(i)
            toReturn.add(toAdd)
            try {
                Thread.sleep(1000)
            } catch (e : InterruptedException) {
                logger.info("I've been interrupted")
            }
        }
        return toReturn
    }

    @Throws(Exception::class)
    fun findByNumber(userNumber: Int): UserDTO? {
        logger.info("findByNumber() invoked: for $userNumber")
        try {
            Thread.sleep(10000)
        } catch (e : InterruptedException) {
            logger.info("I've been interrupted")
        }
        return createUserDTO(userNumber)
    }

//    private fun createUserDTO(id: Int): UserDTO {
//        val toReturn = UserDTO()
//        toReturn.id = id
//        toReturn.name = getSaltString (Random().nextInt(7) + 3)
//        toReturn.surname = getSaltString(Random().nextInt(7) + 3)
//        logger.info("Created $toReturn")
//        return toReturn
//
//    }
//
//    private fun getSaltString(size: Int): String {
//        val SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
//        val salt = StringBuilder()
//        val rnd = Random()
//        while (salt.length < size) { // length of the random string.
//            val index = rnd.nextInt(SALTCHARS.length)
//            salt.append(SALTCHARS[index])
//        }
//        val saltStr = salt.toString()
//        return saltStr
//    }
}
