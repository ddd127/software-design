package software.design.lab10.events.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TicketEvent(
    val eventId: Long,
    val ticketId: Long,
    val data: TicketEventData,
)
