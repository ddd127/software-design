package software.design.lab10.events.applicaiton.model.statistic

import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
data class TicketStatistic(
    val averageVisitsPerTicket: Double,
    val averageVisitDuration: Duration,
)
