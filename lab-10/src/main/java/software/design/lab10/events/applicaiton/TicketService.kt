package software.design.lab10.events.applicaiton

import kotlinx.datetime.Clock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import software.design.lab10.events.applicaiton.model.Ticket
import software.design.lab10.events.domain.model.TicketEvent
import software.design.lab10.events.domain.model.TicketEventData
import software.design.lab10.events.domain.repository.IdsGenerationRepository
import software.design.lab10.events.domain.repository.TicketEventRepository

@Service
class TicketService @Autowired constructor(
    private val clock: Clock,
    private val idsGenerator: IdsGenerationRepository,
    private val eventRepository: TicketEventRepository,
) {

    @Transactional
    fun createTicket(visits: Int): Ticket {
        val ticketId = idsGenerator.generateTicketId()
        val event = TicketEvent(
            idsGenerator.generateEventId(),
            ticketId,
            TicketEventData.Create(visits),
        )
        eventRepository.addEvent(event)
        return getTicket(ticketId)
    }

    @Transactional
    fun checkIn(ticketId: Long): Boolean {
        val ticket = getTicket(ticketId)
        if (ticket.visits <= 0) {
            return false
        }
        eventRepository.addEvent(
            TicketEvent(
                idsGenerator.generateEventId(),
                ticketId,
                TicketEventData.CheckIn(clock.now()),
            ),
        )
        return true
    }

    @Transactional
    fun checkOut(ticketId: Long) {
        eventRepository.addEvent(
            TicketEvent(
                idsGenerator.generateEventId(),
                ticketId,
                TicketEventData.CheckOut(clock.now()),
            ),
        )
    }

    @Transactional
    fun extend(ticketId: Long, visits: Int): Ticket {
        getTicket(ticketId)
        eventRepository.addEvent(
            TicketEvent(
                idsGenerator.generateEventId(),
                ticketId,
                TicketEventData.Extend(visits),
            ),
        )
        return getTicket(ticketId)
    }

    @Transactional
    fun getTicket(ticketId: Long): Ticket =
        getTickets(setOf(ticketId))[ticketId]
            ?: throw IllegalStateException(
                "Failed to perform ticket action, ticket id = $ticketId",
            )

    @Transactional
    fun getTickets(ticketIds: Set<Long>): Map<Long, Ticket> {
        val rawEvents = eventRepository.getTicketEvents(ticketIds)
        val eventByTicketId = rawEvents.groupBy { it.ticketId }

        return ticketIds.mapNotNull { ticketId ->
            val events = eventByTicketId[ticketId] ?: return@mapNotNull null
            if (events.any { it.data == TicketEventData.Delete }) {
                return@mapNotNull null
            }
            val visits = events.map { it.data }.fold(0) { visits, event ->
                when (event) {
                    is TicketEventData.Create -> event.visits
                    is TicketEventData.Extend -> visits + event.additionalVisits
                    is TicketEventData.CheckIn -> visits - 1
                    is TicketEventData.CheckOut -> visits
                    is TicketEventData.Delete -> 0
                }
            }
            return@mapNotNull ticketId to Ticket(
                ticketId,
                visits,
                events,
            )
        }.toMap()
    }
}
