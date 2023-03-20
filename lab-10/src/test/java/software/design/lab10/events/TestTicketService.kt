package software.design.lab10.events

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import software.design.lab10.events.applicaiton.TicketService
import software.design.lab10.events.applicaiton.model.Ticket
import software.design.lab10.events.domain.model.TicketEvent
import software.design.lab10.events.domain.model.TicketEventData
import kotlin.test.assertEquals

@SpringBootTest(
    classes = [TestConfiguration::class],
)
class TestTicketService {

    @Autowired
    private lateinit var ticketService: TicketService

    @Test
    fun `test create ticket`() {
        val ticket = ticketService.createTicket(127)
        assertEquals(127, ticket.visits)
        assertEquals(1, ticket.history.size)
        assertEquals(
            TicketEventData.Create(127).let(::listOf),
            ticket.history.map { it.data },
        )
    }

    @Test
    fun `test get ticket`() {
        val ticket = ticketService.createTicket(127)
            .let(Ticket::ticketId)
            .let(ticketService::getTicket)
        assertEquals(127, ticket.visits)
        assertEquals(1, ticket.history.size)
        assertEquals(
            TicketEventData.Create(127).let(::listOf),
            ticket.history.map { it.data },
        )
    }

    @Test
    fun `test check flow ticket`() {
        var ticket = ticketService.createTicket(127)
        assertEquals(127, ticket.visits)
        assertEquals(1, ticket.history.size)
        assertEquals(
            TicketEventData.Create(127).let(::listOf),
            ticket.history.map { it.data },
        )

        ticketService.checkIn(ticket.ticketId)
        ticketService.checkOut(ticket.ticketId)
        ticket = ticketService.getTicket(ticket.ticketId)
        assertEquals(126, ticket.visits)
        assertEquals(3, ticket.history.size)
        assertEquals(
            ticket.history.map { event ->
                event.copy(eventId = 0)
            },
            listOf(
                TicketEventData.Create(127),
                TicketEventData.CheckIn(TestClock.now()),
                TicketEventData.CheckOut(TestClock.now()),
            ).map { data ->
                TicketEvent(
                    0,
                    ticket.ticketId,
                    data,
                )
            },
        )

        ticket = ticketService.extend(ticket.ticketId, 100)
        assertEquals(226, ticket.visits)
        assertEquals(4, ticket.history.size)
        assertEquals(
            ticket.history.last().data,
            TicketEventData.Extend(100),
        )
    }
}
