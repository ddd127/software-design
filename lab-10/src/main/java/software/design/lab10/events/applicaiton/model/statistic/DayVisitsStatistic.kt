package software.design.lab10.events.applicaiton.model.statistic

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class DayVisitsStatistic(
    val dayToCount: Map<LocalDate, Long>,
)
