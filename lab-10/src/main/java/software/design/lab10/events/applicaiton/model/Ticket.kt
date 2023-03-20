package software.design.lab10.events.applicaiton.model

import kotlinx.serialization.Serializable
import software.design.lab10.events.domain.model.TicketEvent

@Serializable
data class Ticket(
    val ticketId: Long,
    val visits: Int,
    val history: List<TicketEvent>,
)
