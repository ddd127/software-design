package software.design.lab7.profiler.impl

import java.time.Duration

data class StatisticItem(
    val count: Long,
    val min: Duration,
    val max: Duration,
    val sum: Duration,
) {

    val avg = sum.dividedBy(count)

    constructor(duration: Duration) : this(1L, duration, duration, duration)

    operator fun plus(duration: Duration): StatisticItem =
        StatisticItem(
            count.inc(),
            minOf(this.min, duration),
            maxOf(this.max, duration),
            this.sum.plus(duration),
        )
}
