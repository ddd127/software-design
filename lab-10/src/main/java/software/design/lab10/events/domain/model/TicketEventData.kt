package software.design.lab10.events.domain.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
sealed interface TicketEventData {

    @Serializable
    data class Create(val visits: Int) : TicketEventData

    @Serializable
    data class Extend(val additionalVisits: Int) : TicketEventData

    @Serializable
    object Delete : TicketEventData

    @Serializable
    data class CheckIn(val date: Instant) : TicketEventData

    @Serializable
    data class CheckOut(val date: Instant) : TicketEventData
}
