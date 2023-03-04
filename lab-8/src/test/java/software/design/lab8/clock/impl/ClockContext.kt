package software.design.lab8.clock.impl

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.time.Duration

class ClockContext(
    private var current: Instant,
) {
    val clock = object : Clock {
        override fun now(): Instant = current
    }

    fun skip(duration: Duration) {
        current += duration
    }

    companion object {

        fun withClockContext(
            start: Instant = Instant.fromEpochMilliseconds(0),
            block: ClockContext.() -> Unit,
        ) {
            ClockContext(start).block()
        }
    }
}
