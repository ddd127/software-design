package software.design.lab8.clock

interface EventStatisticService {
    fun incEvent(name: String)
    fun getEventStatisticByName(name: String): EventStatistic.NamedEventStatistic?
    fun getAllEventStatistic(): EventStatistic.SummaryEventStatistic
    fun printStatistic()
}
