@file:JvmName("ClientActor")

package net.microservices.tutorial.actorclientservice.actors

import akka.actor.*
import akka.remote.AssociatedEvent
import akka.remote.DisassociatedEvent
import net.microservices.tutorial.messages.AkkaMessage
import net.microservices.tutorial.messages.AkkaResponse
import net.microservices.tutorial.messages.ServerActorRegistration
import java.util.*
import java.util.logging.Logger

/**
 * Created by Gabriele Cardosi - gcardosi@cardosi.net on 07/05/17.
 */

open class ClientActor() : AbstractActor() {


    protected var logger = Logger.getLogger(ClientActor::class.java.simpleName)

    //subscribe to MemberUp events
    override fun preStart() {
        context.system.eventStream().subscribe(self, DisassociatedEvent::class.java)
        context.system.eventStream().subscribe(self, DeadLetter::class.java)
    }


    private var remoteActor: ActorRef? = null

    private val pendingMessages: MutableMap<Int, AkkaMessage> = HashMap()

    internal var active: Receive = receiveBuilder()
            .match(AkkaMessage::class.java) { s ->
                logger.info("Received " + s)
                remoteActor?.tell(s, self)
                pendingMessages.put(s.id, s)
            }
            .match(AkkaResponse::class.java) { s ->
                logger.info("Received " + s)
                pendingMessages.remove(s.id)
                logger.info("We still have ${pendingMessages.size} pending messages")
            }
            .match(Terminated::class.java) {
                terminated ->
                logger.warning("ActorServer terminated")
                context.unwatch(remoteActor)
                context.become(inactive, true)
                remoteActor = null
            }
            .match(ReceiveTimeout::class.java) { x ->
                logger.warning("ReceiveTimeout")
            }
            .match(DeadLetter::class.java) {
                deadLetter ->
                logger.warning("DeadLetter ${deadLetter.message()}")
            }
            .match(DisassociatedEvent::class.java) {
                disassociatedEvent ->
                logger.warning("DisassociatedEvent ${disassociatedEvent.remoteAddress}")
                logger.info("unbecome $self")
                context.unwatch(remoteActor)
                context.become(inactive, true)
                remoteActor = null
            }
            .match(ServerActorRegistration::class.java) {
                if (remoteActor != null) {
                    context.unwatch(remoteActor)
                }
                remoteActor = sender
                context.watch(remoteActor)
            }
            .build()

    internal var inactive: Receive = receiveBuilder()
            .match(ServerActorRegistration::class.java) {
                remoteActor = sender
                context.watch(remoteActor)
                context.become(active, true)
            }
            .match(AssociatedEvent::class.java) {
                associatedEvent ->
                logger.warning("AssociatedEvent ${associatedEvent.remoteAddress}")
            }
            .match(ReceiveTimeout::class.java) {
                x ->
                logger.warning("ReceiveTimeout : " + x)
            }
            .build()

    override fun unhandled(message: Any?) {
        logger.info("Unhandled  " + message?.javaClass.toString())
        super.unhandled(message)
    }

    override fun createReceive(): Receive {
        return inactive
    }

}
