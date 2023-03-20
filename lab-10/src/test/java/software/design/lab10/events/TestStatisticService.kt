package software.design.lab10.events

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.jooq.DSLContext
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import software.design.lab10.events.applicaiton.StatisticService
import software.design.lab10.events.applicaiton.TicketService
import software.design.lab10.events.applicaiton.model.Ticket
import software.design.lab10.events.applicaiton.model.statistic.TicketStatistic
import software.design.lab10.events.domain.db.Tables
import java.time.Month
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes

@SpringBootTest(
    classes = [TestConfiguration::class],
)
class TestStatisticService {

    @Autowired
    private lateinit var ticketService: TicketService

    @Autowired
    private lateinit var statisticService: StatisticService

    @Autowired
    private lateinit var dslContext: DSLContext

    @BeforeEach
    fun cleanDb() {
        statisticService.clear()
        dslContext.deleteFrom(Tables.TICKET_EVENTS).execute()
    }

    @Test
    fun `test day statistic`() {
        val startTime = TestClock.now()
        val localDate = startTime.toLocalDateTime(TimeZone.UTC).date
        val ticketId = ticketService.createTicket(127).let(Ticket::ticketId)
        ticketService.checkIn(ticketId)
        ticketService.checkOut(ticketId)
        TestClock.set(startTime + 1.days)
        ticketService.checkIn(ticketId)
        ticketService.checkOut(ticketId)
        ticketService.checkIn(ticketId)
        ticketService.checkOut(ticketId)
        TestClock.set(startTime + 2.days)
        ticketService.checkIn(ticketId)
        ticketService.checkOut(ticketId)

        val dayStatistic = statisticService.getDayVisitStatistic()
        assertEquals(
            mapOf(
                localDate to 1L,
                localDate.plus(DateTimeUnit.DayBased(1)) to 2L,
                localDate.plus(DateTimeUnit.DayBased(2)) to 1L,
                ),
            dayStatistic.dayToCount,
        )
    }

    @Test
    fun `test ticket statistic`() {
        TestClock.set(
            LocalDateTime(2023, Month.DECEMBER, 31, 23, 50, 0, 0)
                .toInstant(TimeZone.UTC),
        )
        val ticketId1 = ticketService.createTicket(127).let(Ticket::ticketId)
        val ticketId2 = ticketService.createTicket(127).let(Ticket::ticketId)

        ticketService.checkIn(ticketId1)
        TestClock.set(TestClock.now() + 15.minutes)
        ticketService.checkOut(ticketId1)

        ticketService.checkIn(ticketId1)
        TestClock.set(TestClock.now() + 45.minutes)
        ticketService.checkOut(ticketId1)

        ticketService.checkIn(ticketId2)
        TestClock.set(TestClock.now() + 40.minutes)
        ticketService.checkOut(ticketId2)

        ticketService.checkIn(ticketId1)
        TestClock.set(TestClock.now() + 20.minutes)
        ticketService.checkOut(ticketId1)

        ticketService.checkIn(ticketId2)
        TestClock.set(TestClock.now() + 10.minutes)
        ticketService.checkOut(ticketId2)

        ticketService.checkIn(ticketId1)
        TestClock.set(TestClock.now() + 50.minutes)
        ticketService.checkOut(ticketId1)

        val ticketStatistic = statisticService.getTicketStatistic()
        assertEquals(
            TicketStatistic(
                3.0,
                30.minutes,
            ),
            ticketStatistic,
        )
    }
}
