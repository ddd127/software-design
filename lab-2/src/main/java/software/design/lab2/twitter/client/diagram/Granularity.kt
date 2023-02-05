package software.design.lab2.twitter.client.diagram

import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit

enum class Granularity(
    val temporalUnit: TemporalUnit,
) {
    MINUTE(ChronoUnit.MINUTES),
    HOUR(ChronoUnit.HOURS),
    DAY(ChronoUnit.DAYS),
    ;

    val parameterValue: String = name.lowercase()
}
