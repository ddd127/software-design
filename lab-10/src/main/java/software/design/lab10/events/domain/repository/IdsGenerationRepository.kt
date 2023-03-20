package software.design.lab10.events.domain.repository

import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import software.design.lab10.events.domain.db.Sequences

@Repository
class IdsGenerationRepository @Autowired constructor(
    private val dslContext: DSLContext,
) {

    private val ticketIds: Iterator<Long> = generateSequence {
        dslContext.nextvals(Sequences.SQ__TICKET_EVENTS__TICKET_ID, 500)
    }.flatten().iterator()

    private val eventIds: Iterator<Long> = generateSequence {
        dslContext.nextvals(Sequences.SQ__TICKET_EVENTS__TICKET_ID, 500)
    }.flatten().iterator()

    fun generateTicketId(): Long = ticketIds.next()
    fun generateEventId(): Long = eventIds.next()
}
