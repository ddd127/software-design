package software.design.lab10.events.domain.repository

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import software.design.lab10.events.domain.db.Tables.TICKET_EVENTS
import software.design.lab10.events.domain.model.TicketEvent

@Repository
class TicketEventRepository @Autowired constructor(
    private val dslContext: DSLContext,
) {

    fun getEvents(idGt: Long, limit: Int): List<TicketEvent> =
        dslContext.selectFrom(TICKET_EVENTS)
            .where(TICKET_EVENTS.EVENT_ID.greaterThan(idGt))
            .orderBy(TICKET_EVENTS.EVENT_ID)
            .fetch { record ->
                TicketEvent(
                    record.eventId,
                    record.ticketId,
                    Json.decodeFromString(record.eventData),
                )
            }

    fun getTicketEvents(ticketIds: Set<Long>): List<TicketEvent> =
        dslContext.selectFrom(TICKET_EVENTS)
            .where(TICKET_EVENTS.TICKET_ID.`in`(ticketIds))
            .orderBy(TICKET_EVENTS.EVENT_ID)
            .fetch { record ->
                TicketEvent(
                    record.eventId,
                    record.ticketId,
                    Json.decodeFromString(record.eventData),
                )
            }

    fun addEvent(event: TicketEvent) {
        dslContext.insertInto(
            TICKET_EVENTS,
            TICKET_EVENTS.EVENT_ID,
            TICKET_EVENTS.TICKET_ID,
            TICKET_EVENTS.EVENT_DATA,
        ).values(
            DSL.row(
                event.eventId,
                event.ticketId,
                Json.encodeToString(event.data),
            ),
        ).execute()
    }
}
