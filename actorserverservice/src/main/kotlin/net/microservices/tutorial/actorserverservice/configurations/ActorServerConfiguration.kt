@file:JvmName("ActorServerConfiguration")

package net.microservices.tutorial.actorserverservice.configurations


import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import com.netflix.appinfo.InstanceInfo
import com.netflix.discovery.EurekaClient
import com.netflix.discovery.shared.Application
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigValueFactory
import net.microservices.tutorial.actorserverservice.actors.ServerActor
import net.microservices.tutorial.actorserverservice.controllers.HomeController
import net.microservices.tutorial.actorserverservice.services.UsersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import java.util.*
import java.util.logging.Logger

/**
 * Created by Gabriele Cardosi - gcardosi@cardosi.net on 04/11/2016.
 */
@Configuration
@EnableDiscoveryClient
@ComponentScan("net.microservices.tutorial.actorserverservice")
open class ActorServerConfiguration {

    private val SYSTEM_NAME = "ClusterSystem"

    private var logger = Logger.getLogger(ActorServerConfiguration::class.java.simpleName)

    @Value("\${eureka.instance.metadata-map.port}")
    var akkaPort: Int = 0

    @Value("\${akkaclusterservicename.name}")
    var akkaClusterServiceName: String? = null

    @Autowired
    private val eurekaClient: EurekaClient? = null

    @Bean
    open fun actorSystem(usersService: UsersService): ActorSystem? {
        val instanceInfo = getRemoteInstanceInfo()
        if (instanceInfo != null) {
            logger.info("instanceInfo ${instanceInfo.hostName} ")
            return getSystem(instanceInfo, usersService)
        } else {
            logger.severe("instanceInfo NOT FOUND ")
            return null
        }
    }

    private fun getSystem(instanceInfo: InstanceInfo, usersService: UsersService): ActorSystem {
        val defaultConfiguration = getDefaultConfiguration(instanceInfo)
        val system = ActorSystem.create(SYSTEM_NAME, defaultConfiguration)
        system.actorOf(Props.create(ServerActor::class.java, usersService), "serverActor")
        return system
    }

    private fun getRemoteInstanceInfo(): InstanceInfo? {
        logger.info("akkaClusterServiceName $akkaClusterServiceName")
        val akkaClusterService: Application? = eurekaClient?.getApplication(akkaClusterServiceName)
        logger.info("akkaClusterService ? " + akkaClusterService!!.name)
        akkaClusterService?.shuffleAndStoreInstances(true)
        val instances: List<InstanceInfo>? = akkaClusterService?.instances
        var toReturn: InstanceInfo? = null
        if (instances != null && instances.size > 0) {
            toReturn = instances[0]
        }
        return toReturn
    }

    private fun getDefaultConfiguration(instanceInfo: InstanceInfo): Config {
        val hostName = eurekaClient?.applicationInfoManager?.info?.ipAddr ?: "hostname"
        logger.info("hostName $hostName ")
        val clusterServiceHost = instanceInfo.ipAddr
        logger.info("clusterServiceHost $clusterServiceHost")
        val seedNodes = instanceInfo.metadata.filter { kv -> kv.key.startsWith("seed") }.map { kv -> "akka.tcp://$SYSTEM_NAME@$clusterServiceHost:${kv.value}" }
        seedNodes.forEach {
            seedNode -> logger.info("seedNode $seedNode")
        }
        var toReturn: Config = ConfigFactory.defaultApplication()
                .withValue("akka.remote.netty.tcp.hostname", ConfigValueFactory.fromAnyRef(hostName))
                .withValue("akka.remote.netty.tcp.port", ConfigValueFactory.fromAnyRef(akkaPort))
        if (!seedNodes.isEmpty()) {
            toReturn = toReturn
                    .withValue("akka.cluster.seed-nodes", ConfigValueFactory.fromIterable(seedNodes))
        }
        return toReturn
    }
}
