package software.design.lab10.events

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

object TestClock : Clock {
    private var current: Instant = Instant.DISTANT_PAST

    override fun now(): Instant = current

    fun set(instant: Instant) {
        current = instant
    }
}
