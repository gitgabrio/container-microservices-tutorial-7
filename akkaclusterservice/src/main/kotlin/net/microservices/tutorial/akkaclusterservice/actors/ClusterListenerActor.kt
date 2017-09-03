@file:JvmName("ClusterListenerActor")

package net.microservices.tutorial.akkaclusterservice.actors

import akka.actor.AbstractActor
import akka.cluster.Cluster
import akka.cluster.ClusterEvent
import akka.cluster.ClusterEvent.*
import akka.event.Logging
import net.microservices.tutorial.akkaclusterservice.services.HomeService

class ClusterListenerActor(val homeService: HomeService) : AbstractActor() {


    internal var log = Logging.getLogger(context.system(), this)
    internal var cluster = Cluster.get(context.system())

    //subscribe to cluster changes
    override fun preStart() {
        cluster.subscribe(self(), ClusterEvent.initialStateAsEvents(),
                MemberEvent::class.java, UnreachableMember::class.java)
    }

    //re-subscribe when restart
    override fun postStop() {
        cluster.unsubscribe(self())
    }

    override fun createReceive(): AbstractActor.Receive {
        return receiveBuilder()
                .match(MemberUp::class.java) { mUp ->
                    log.info("Member is Up: {}", mUp.member())
                    homeService.nodeStatusMap.put(mUp.member().uniqueAddress().address(), HomeService.MemberDetail(mUp.member().roles, HomeService.NODE_STATUS.UP))
                }
                .match(UnreachableMember::class.java) { mUnreachable ->
                    log.info("Member detected as unreachable: {}", mUnreachable.member())
                    homeService.nodeStatusMap.put(mUnreachable.member().uniqueAddress().address(), HomeService.MemberDetail(mUnreachable.member().roles, HomeService.NODE_STATUS.UNREACHABLE))
                }
                .match(MemberRemoved::class.java) { mRemoved ->
                    log.info("Member is Removed: {}", mRemoved.member())
                    homeService.nodeStatusMap.put(mRemoved.member().uniqueAddress().address(), HomeService.MemberDetail(mRemoved.member().roles, HomeService.NODE_STATUS.REMOVED))
                }
                .match(MemberEvent::class.java) { message ->
                    log.info("Member is {}: {}", message.member().status(), message.member())
                    // ignore
                }
                .build()
    }
}
