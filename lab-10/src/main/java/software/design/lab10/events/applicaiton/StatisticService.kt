package software.design.lab10.events.applicaiton

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import software.design.lab10.events.applicaiton.model.statistic.DayVisitsStatistic
import software.design.lab10.events.applicaiton.model.statistic.TicketStatistic
import software.design.lab10.events.domain.model.TicketEvent
import software.design.lab10.events.domain.model.TicketEventData
import software.design.lab10.events.domain.repository.TicketEventRepository
import kotlin.time.Duration

@Service
class StatisticService @Autowired constructor(
    private val eventRepository: TicketEventRepository,
    private val ticketService: TicketService,
) {

    private var lastEventId: Long = 0
    private val dayToVisits: MutableMap<LocalDate, Long> = mutableMapOf()
    private val ticketToStatistic: MutableMap<Long, TicketDataHolder> = mutableMapOf()

    @Synchronized
    fun getDayVisitStatistic(): DayVisitsStatistic {
        updateStats()
        return DayVisitsStatistic(dayToVisits.toMap())
    }

    fun clear() {
        lastEventId = 0
        dayToVisits.clear()
        ticketToStatistic.clear()
    }

    @Synchronized
    fun getTicketStatistic(): TicketStatistic {
        updateStats()
        val averageVisits = ticketToStatistic.map { (_, holder) ->
            holder.totalVisits
        }.let { list -> list.sum().toDouble() / list.size }
        val averageVisitDuration = ticketToStatistic.toList()
            .fold(Duration.ZERO to 0) { (duration, visits), (_, holder) ->
                (duration + holder.totalVisitDuration) to
                        (visits + holder.totalVisits)
            }.let { (duration, visits) ->
                duration.div(visits)
            }
        return TicketStatistic(
            averageVisits,
            averageVisitDuration,
        )
    }

    private fun updateStats() {
        val newEventSequence = generateSequence {
            eventRepository.getEvents(lastEventId, 500)
                .takeIf { it.isNotEmpty() }
                ?.also {
                    lastEventId = it.last().eventId
                }
        }
        newEventSequence.forEach { chunk ->
            processEventsForTicketStatistic(chunk)
            processEventsForDayVisitStatistic(chunk)
        }
    }

    private fun processEventsForDayVisitStatistic(
        chunk: List<TicketEvent>,
    ) {
        chunk.map { it.data }.forEach { event ->
            if (event !is TicketEventData.CheckIn) return@forEach
            val date = event.date.toLocalDateTime(TimeZone.UTC).date
            dayToVisits.merge(date, 1L) { a, b -> a + b }
        }
    }

    private fun processEventsForTicketStatistic(
        chunk: List<TicketEvent>,
    ) {
        val tickets = chunk.map { it.ticketId }.toSet()
            .let(ticketService::getTickets)
        val updatedStats = tickets.values.associate { ticket ->
            val events = ticket.history.map { it.data }
            val totalVisits = events.filterIsInstance<TicketEventData.CheckOut>().size
            val totalDuration = events.fold(
                (null as TicketEventData.CheckIn?) to Duration.ZERO,
            ) { (checkIn, duration), current ->
                when {
                    checkIn == null && current is TicketEventData.CheckIn -> {
                        current to duration
                    }

                    checkIn != null && current is TicketEventData.CheckOut -> {
                        null to duration + current.date.minus(checkIn.date)
                    }

                    else -> null to duration
                }
            }.second

            ticket.ticketId to TicketDataHolder(
                totalVisits,
                totalDuration,
            )
        }
        ticketToStatistic.putAll(updatedStats)
    }

    private class TicketDataHolder(
        val totalVisits: Int,
        val totalVisitDuration: Duration,
    )
}
