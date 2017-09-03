package net.microservices.tutorial.persistenceservice.controllers



import net.microservices.tutorial.dto.InterfaceDTO
import net.microservices.tutorial.persistenceservice.entities.InterfaceEntity
import net.microservices.tutorial.persistenceservice.exceptions.EntityNotFoundException
import org.springframework.data.repository.CrudRepository

import java.io.Serializable
import java.util.logging.Logger


abstract class AbstractEntityController<D : InterfaceDTO, ID : Serializable, T : InterfaceEntity, E : CrudRepository<T, ID>>
/**
 * Create an instance plugging in the repository of Users.

 * @param repository A `CrudRepository, ID>`  implementation
 */
(protected var repository: E) {

    protected var logger = Logger.getLogger(AbstractEntityController::class.java.simpleName)

    init {
        logger.info("Repository says system has " + repository.count() + " entities")
    }

    /**
     * Fetch a <code>&lt;T&gt;</code> entity with the specified <code>&lt;ID&gt;</code>  id.

     * @param id <code>&lt;ID&gt;</code>  id.
     * *
     * @return The  <code>&lt;T&gt;</code>  entity if found.
     * *
     * @throws EntityNotFoundException If the number is not recognised.
     */
    open fun byId(id: ID): T {
        logger.info("AbstractEntityController byId() invoked: " + id)
        if (!repository.exists(id)) {
            throw EntityNotFoundException(javaClass.simpleName.toString(), id.toString())
        }
        val toReturn = repository.findOne(id)
        logger.info("AbstractEntityController byId() found: " + toReturn)
        return toReturn
    }

    /**
     * Fetch all <code>List&lt;T&gt;</code>  entities

     * @return All the  <code>&lt;T&gt;</code>  entities found.
     */
    open fun findAll(): Iterable<T> {
        logger.info("AbstractEntityController findAll() invoked")
        val toReturn = repository.findAll()
        logger.info("AbstractEntityController findAll() found: " + toReturn)
        return toReturn
    }

    /**
     * Save a <code>&lt;T&gt;</code> entity

     * @param toSave <code>&lt;T&gt;</code>  entity  to save
     * *
     * @return The saved <code>&lt;T&gt;</code>  entity
     */
    open fun save(toSave: T): T {
        logger.info("persistence-service save() invoked: " + toSave)
        val toReturn = repository.save(toSave)
        logger.info("persistence-service save() saved: " + toReturn)
        return toReturn
    }
}
