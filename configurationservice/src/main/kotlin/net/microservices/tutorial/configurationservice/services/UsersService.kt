@file:JvmName("UsersService")

package net.microservices.tutorial.configurationservice.services

import net.microservices.tutorial.dto.UserDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.*
import org.springframework.web.context.request.async.DeferredResult
import java.lang.Exception
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger
import javax.annotation.PostConstruct

/**
 * Hide the access to the microservice inside this local service.

l
 */
@Service
class UsersService(persistenceServiceUrl: String, timeConsumingserviceUrl: String) {

    @Autowired
    @LoadBalanced
    private var restTemplate: RestTemplate? = null

    @Autowired
    @LoadBalanced
    private var asyncRestTemplate: AsyncRestTemplate? = null

    private var persistenceServiceUrl: String

    private var timeConsumingserviceUrl: String

    private var logger = Logger.getLogger(UsersService::class.java.name)

    init {
        this.persistenceServiceUrl = if (persistenceServiceUrl.startsWith("http"))
            persistenceServiceUrl
        else
            "http://" + persistenceServiceUrl
        this.timeConsumingserviceUrl = if (timeConsumingserviceUrl.startsWith("http"))
            timeConsumingserviceUrl
        else
            "http://" + timeConsumingserviceUrl
    }

    /**
     * The RestTemplate works because it uses a custom request-factory that uses
     * Ribbon to look-up the service to use. This method simply exists to show
     * this.
     */
    @PostConstruct
    fun demoOnly() {
        // Can't do this in the constructor because the RestTemplate injection
        // happens afterwards.
        logger.warning("The RestTemplate request factory is " + restTemplate!!.requestFactory.javaClass)
    }

    @Throws(Exception::class)
    fun findAll(): List<UserDTO>? {
        logger.info("findAll() invoked")
        var users: Array<UserDTO>? = null
        try {
            users = restTemplate!!.getForObject(persistenceServiceUrl + "/persons/", Array<UserDTO>::class.java)
        } catch (e: HttpClientErrorException) { // 404
            // Nothing found
            return null
        }
        if (users == null || users.size == 0)
            return null
        else
            return Arrays.asList(*users)
    }

    @Throws(Exception::class)
    fun findByNumber(userNumber: Integer): UserDTO? {
        logger.info("findByNumber() invoked: for $userNumber")
        try {
            return restTemplate!!.getForObject(persistenceServiceUrl + "/persons/{id}", UserDTO::class.java, userNumber)
        } catch (e: HttpClientErrorException) { // 404
            // Nothing found
            return null
        }
    }

    fun findBySurnameAndName(surname: String, name: String): UserDTO? {
        logger.info("findBySurnameAndName() invoked:  for $surname $name")
        var user: UserDTO? = null
        try {
            user = restTemplate!!.getForObject(persistenceServiceUrl + "/persons/{surname}/{name}", UserDTO::class.java, surname, name)
        } catch (e: HttpClientErrorException) { // 404
            // Nothing found
        }
        return user
    }

    fun findBySurname(surname: String): List<UserDTO>? {
        logger.info("findBySurname() invoked:  for $surname ")
        var users: Array<UserDTO>? = null
        try {
            users = restTemplate!!.getForObject(persistenceServiceUrl + "/persons/surname/{surname}", Array<UserDTO>::class.java, surname)
        } catch (e: HttpClientErrorException) { // 404
            // Nothing found
        }
        if (users == null || users.size == 0)
            return null
        else
            return Arrays.asList(*users)
    }

    @Throws(Exception::class)
    fun saveUser(newUser: UserDTO): UserDTO {
        logger.info("saveUser() invoked: for " + newUser)
        try {
            return restTemplate!!.postForObject(persistenceServiceUrl + "/persons/save", newUser, UserDTO::class.java) ?: throw Exception("Failed to save user")
        } catch (e: HttpClientErrorException) { // 404
            // Nothing found
            throw e
        }
    }

    @Throws(Exception::class)
    fun findAsyncAll(): List<UserDTO>? {
        logger.info("findAsyncAll() invoked")
        var users: List<UserDTO>? = null
        try {
            val method = HttpMethod.GET
            val responseType = genericClass<List<UserDTO>>()
            //create request entity using HttpHeaders
            val headers = HttpHeaders()
            headers.contentType = MediaType.TEXT_PLAIN
            val requestEntity = HttpEntity<String>("params", headers)
            val future = asyncRestTemplate?.exchange(timeConsumingserviceUrl + "/deferredpersons/", method, requestEntity, responseType)
            //waits for the result
            val entity = future?.get()
            //prints body source code for the given URL
            users = entity?.body
        } catch (e: Exception) {
            logger.log(Level.SEVERE, e.message, e)
        }
        return users
    }

    @Throws(Exception::class)
    fun findAsyncByNumber(id: Int): UserDTO? {
        logger.info("findAsyncByNumber() invoked")
        var user: UserDTO? = null
        try {
            val method = HttpMethod.GET
            val responseType = genericClass<UserDTO>()
            //create request entity using HttpHeaders
            val headers = HttpHeaders()
            headers.contentType = MediaType.TEXT_PLAIN
            val requestEntity = HttpEntity<String>("params", headers)
            val future = asyncRestTemplate?.exchange(timeConsumingserviceUrl + "/deferredpersons/{id}", method, requestEntity, responseType, id)
            //waits for the result
            val entity = future?.get()
            //prints body source code for the given URL
            user = entity?.body
        } catch (e: Exception) {
            logger.log(Level.SEVERE, e.message, e)
        }
        return user
    }


    private inline fun <reified T : Any> genericClass(): Class<T> = T::class.java
}
