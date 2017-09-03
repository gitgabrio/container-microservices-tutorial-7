@file:JvmName("PersistenceConfiguration")
package net.microservices.tutorial.persistenceservice.configurations

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder
import org.springframework.boot.context.properties.ConfigurationProperties
//import org.springframework.boot.orm.jpa.EntityScan
import org.springframework.context.annotation.*
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import java.util.logging.Logger
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

/**
 * The users Spring configuration.
 *
 */
@Configuration
@ComponentScan("net.microservices.tutorial.persistenceservice")
@EntityScan("net.microservices.tutorial.persistenceservice.entities")
@EnableJpaRepositories("net.microservices.tutorial.persistenceservice.repositories")
open class PersistenceConfiguration {

    protected var logger: Logger

    init {
        logger = Logger.getLogger(javaClass.name)
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "testDatasource")
    open fun dataSource(): DataSource {
        logger.info("dataSource() invoked")
        return DataSourceBuilder.create().build()
    }



}
