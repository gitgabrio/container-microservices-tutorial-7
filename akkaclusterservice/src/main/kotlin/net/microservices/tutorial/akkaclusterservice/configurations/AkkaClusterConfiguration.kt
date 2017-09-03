@file:JvmName("AkkaClusterConfiguration")

package net.microservices.tutorial.akkaclusterservice.configurations


import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import com.netflix.discovery.EurekaClient
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigValueFactory
import net.microservices.tutorial.akkaclusterservice.actors.ClusterListenerActor
import net.microservices.tutorial.akkaclusterservice.actors.MetricsListenerActor
import net.microservices.tutorial.akkaclusterservice.controllers.HomeController
import net.microservices.tutorial.akkaclusterservice.services.HomeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

/**
 * Created by Gabriele Cardosi - gcardosi@cardosi.net on 04/11/2016.
 */
@Configuration
@EnableDiscoveryClient
@ComponentScan("net.microservices.tutorial.akkaclusterservice")
open class AkkaClusterConfiguration {

    private val SYSTEM_NAME = "ClusterSystem"

    @Value("\${eureka.instance.metadata-map.port}")
    var akkaPort: Int = 0

    @Value("\${eureka.instance.metadata-map.seed_port1}")
    var seedPort1: Int = 0

    @Value("\${eureka.instance.metadata-map.seed_port2}")
    var seedPort2: Int = 0

    @Autowired
    private val eurekaClient: EurekaClient? = null


    @Bean
    open fun akkaClusterSystem(homeService: HomeService): ActorSystem {
        startClusterListener(seedPort1, "clusterListenerActor1", homeService)
        startClusterListener(seedPort2, "clusterListenerActor2", homeService)
        val defaultApplication: Config = getConfig(akkaPort)
        val toReturn = ActorSystem.create(SYSTEM_NAME, defaultApplication)
        return toReturn
    }

    @Bean
    open fun metricsListenerActor(akkaClusterSystem: ActorSystem, homeService: HomeService): ActorRef {
        val toReturn = akkaClusterSystem.actorOf(Props.create(MetricsListenerActor::class.java, homeService), "metricsListener")
        return toReturn
    }

    @Bean
    open fun homeController(): HomeController {
        return HomeController()
    }

    private fun startClusterListener(seedPort: Int, actorName: String, homeService: HomeService) {
        val defaultApplication: Config = getConfig(seedPort)
        val system = ActorSystem.create(SYSTEM_NAME, defaultApplication)
        system.actorOf(Props.create(ClusterListenerActor::class.java, homeService), actorName)
    }

    private fun getConfig(tcpPort: Int): Config {
        val hostName = eurekaClient?.applicationInfoManager?.info?.ipAddr ?: "hostname"
        val seedNodes = listOf("akka.tcp://$SYSTEM_NAME@$hostName:$seedPort1", "akka.tcp://$SYSTEM_NAME@$hostName:$seedPort2")
        val toReturn: Config = ConfigFactory.defaultApplication()
                .withValue("akka.remote.netty.tcp.hostname", ConfigValueFactory.fromAnyRef(hostName))
                .withValue("akka.remote.netty.tcp.port", ConfigValueFactory.fromAnyRef(tcpPort))
                .withValue("akka.cluster.seed-nodes", ConfigValueFactory.fromIterable(seedNodes))
        return toReturn
    }

}
