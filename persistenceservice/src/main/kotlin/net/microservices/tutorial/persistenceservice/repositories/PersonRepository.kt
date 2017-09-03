@file:JvmName("PersonRepository")
package net.microservices.tutorial.persistenceservice.repositories

import net.microservices.tutorial.persistenceservice.entities.UserEntity
import org.springframework.data.repository.CrudRepository

/**
 * Repository for UserEntity data implemented using Spring Data JPA.

 * l
 */
interface PersonRepository : CrudRepository<UserEntity, Int> {

    fun findBySurnameAndName(surname : String, name: String) : UserEntity

    fun findBySurname(surname : String) : List<UserEntity>
}
