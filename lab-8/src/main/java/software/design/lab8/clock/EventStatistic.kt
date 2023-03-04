package software.design.lab8.clock

import kotlin.time.DurationUnit

sealed interface EventStatistic {

    val requestsPerGranularity: Double
    val granularity: DurationUnit

    data class NamedEventStatistic(
        val name: String,
        override val requestsPerGranularity: Double,
        override val granularity: DurationUnit,
    ) : EventStatistic

    data class SummaryEventStatistic(
        override val requestsPerGranularity: Double,
        override val granularity: DurationUnit,
        val nameToStatistic: Map<String, NamedEventStatistic>,
    ) : EventStatistic
}
