package software.design.lab8.clock.impl

import org.junit.jupiter.api.Test
import software.design.lab8.clock.EventStatistic
import software.design.lab8.clock.EventStatisticService
import software.design.lab8.clock.impl.ClockContext.Companion.withClockContext
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit

class DefaultEventStatisticServiceTest {

    @Test
    fun `test empty`() = withClockContext {
        val service: EventStatisticService = DefaultEventStatisticService(clock)
        assertEquals(
            emptySummary(),
            service.getAllEventStatistic(),
        )
    }

    @Test
    fun `test expired`() = withClockContext {
        val service: EventStatisticService = DefaultEventStatisticService(clock)
        repeat(5) { service.incEvent("test1") }
        skip(30.minutes)
        assertEquals(
            listOf(
                "test1" to 5,
            ).asSummaryEvents(),
            service.getAllEventStatistic(),
        )

        repeat(3) { service.incEvent("test2") }
        skip(45.minutes)
        assertEquals(
            listOf(
                "test2" to 3,
            ).asSummaryEvents(),
            service.getAllEventStatistic(),
        )
    }

    @Test
    fun `test multiple stats`() = withClockContext {
        val service: EventStatisticService = DefaultEventStatisticService(clock)

        repeat(20) { service.incEvent("test1") }
        skip(30.minutes)
        assertEquals(
            listOf(
                "test1" to 20,
            ).asSummaryEvents(),
            service.getAllEventStatistic(),
        )

        repeat(5) { service.incEvent("test2") }
        skip(15.minutes)
        assertEquals(
            listOf(
                "test1" to 20,
                "test2" to 5,
            ).asSummaryEvents(),
            service.getAllEventStatistic(),
        )

        repeat(10) { service.incEvent("test2") }
        skip(30.minutes)
        assertEquals(
            listOf(
                "test2" to 15,
            ).asSummaryEvents(),
            service.getAllEventStatistic(),
        )

        repeat(15) { service.incEvent("test3") }
        skip(15.minutes)
        assertEquals(
            listOf(
                "test2" to 15,
                "test3" to 15,
            ).asSummaryEvents(),
            service.getAllEventStatistic(),
        )

        skip(15.minutes)
        assertEquals(
            listOf(
                "test2" to 10,
                "test3" to 15,
            ).asSummaryEvents(),
            service.getAllEventStatistic(),
        )

        skip(30.minutes)
        assertEquals(
            listOf(
                "test3" to 15,
            ).asSummaryEvents(),
            service.getAllEventStatistic(),
        )

        skip(30.minutes)
        assertEquals(
            emptySummary(),
            service.getAllEventStatistic(),
        )
    }

    companion object {

        private fun List<Pair<String, Int>>.asSummaryEvents(): EventStatistic.SummaryEventStatistic =
            map { (name, count) ->
                val rpm = count.toDouble() / 60
                EventStatistic.NamedEventStatistic(name, rpm, DurationUnit.MINUTES)
            }.let { list ->
                val rpm = this.sumOf { it.second }.toDouble() / 60
                EventStatistic.SummaryEventStatistic(
                    rpm,
                    DurationUnit.MINUTES,
                    list.associateBy { it.name },
                )
            }

        private fun emptySummary() = EventStatistic.SummaryEventStatistic(
            0.0,
            DurationUnit.MINUTES,
            emptyMap(),
        )
    }
}
