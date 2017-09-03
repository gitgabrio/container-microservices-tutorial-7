package net.microservices.tutorial.akkaclusterservice.services

import akka.actor.Address
import org.springframework.stereotype.Service

/**
 * Created by Gabriele Cardosi - gcardosi@cardosi.net on 19/08/17.
 */
@Service
open class HomeService {

    enum class NODE_STATUS {
        UP,
        UNREACHABLE,
        REMOVED
    }

    var heapMemory: Double = 0.00
    var processors: Int = 0
    var loadAverage: Double = 0.00
    var nodeStatusMap: MutableMap<Address, MemberDetail> = mutableMapOf()


    open class MemberDetail(val roles: Set<String>, val status: NODE_STATUS) {

    }


}