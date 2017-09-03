@file:JvmName("ActorClientConfiguration")

package net.microservices.tutorial.actorclientservice.configurations

import akka.actor.*
import java.util.concurrent.TimeUnit.SECONDS
import com.netflix.appinfo.InstanceInfo
import com.netflix.discovery.EurekaClient
import com.netflix.discovery.shared.Application
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigValueFactory
import net.microservices.tutorial.actorclientservice.actors.ClientActor
import net.microservices.tutorial.classes.createUserDTO
import net.microservices.tutorial.classes.notNull
import net.microservices.tutorial.commands.Command
import net.microservices.tutorial.dto.UserDTO
import net.microservices.tutorial.messages.AkkaMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import scala.concurrent.duration.Duration
import java.util.*
import java.util.logging.Logger

/**
 * Created by Gabriele Cardosi - gcardosi@cardosi.net on 04/11/2016.
 */
@Configuration
@ComponentScan("net.microservices.tutorial.actorclientservice")
open class ActorClientConfiguration {

    private val SYSTEM_NAME = "ClusterSystem"

    private var logger = Logger.getLogger(ActorClientConfiguration::class.java.simpleName)

    @Value("\${eureka.instance.metadata-map.port}")
    var akkaPort: Int = 0


    @Value("\${akkaclusterservicename.name}")
    var akkaClusterServiceName: String? = null


    @Autowired
    private val eurekaClient: EurekaClient? = null

    @Bean
    open fun actorSystem(): ActorSystem? {
        val instanceInfo = getRemoteInstanceInfo()
        if (instanceInfo != null) {
            return getSystem(instanceInfo)
        } else {
            return null
        }
    }

    private fun getSystem(instanceInfo: InstanceInfo): ActorSystem {
        val defaultConfiguration = getDefaultConfiguration(instanceInfo)
        val system = ActorSystem.create(SYSTEM_NAME, defaultConfiguration)
        val clientActor: ActorRef = system.actorOf(Props.create(ClientActor::class.java), "clientActor")
        val r: Random = Random()
        var counter: Int = 1
        system.scheduler().schedule(Duration.create(1, SECONDS),
                Duration.create(1, SECONDS), Runnable {
            val userDto: UserDTO = createUserDTO(r.nextInt(50))
            val command: Command = Command.values()[r.nextInt(4)]
            clientActor.tell(AkkaMessage(userDto, command, counter), null)
            counter++
        }, system.dispatcher())
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
