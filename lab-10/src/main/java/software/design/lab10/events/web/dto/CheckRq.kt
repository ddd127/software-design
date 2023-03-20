package software.design.lab10.events.web.dto

import kotlinx.serialization.Serializable

@Serializable
data class CheckRq(
    val ticketId: Long,
)
