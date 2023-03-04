package software.design.lab8.clock.impl

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import software.design.lab8.clock.EventStatistic
import software.design.lab8.clock.EventStatisticService
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.DurationUnit

class DefaultEventStatisticService(
    private val clock: Clock = Clock.System,
    private val interval: Duration = 1.hours,
    private val granularity: DurationUnit = DurationUnit.MINUTES,
) : EventStatisticService {

    init {
        if (interval.toLong(granularity) <= 0) {
            throw IllegalArgumentException("Granularity must be less than one interval")
        }
    }

    private val divisor = interval.toDouble(granularity)

    private val nameToCount: MutableMap<String, Long> = mutableMapOf()
    private val eventQueue: ArrayDeque<Pair<String, Instant>> = ArrayDeque()

    override fun incEvent(name: String) {
        cleanQueue()
        synchronized(this) {
            eventQueue.addLast(name to clock.now())
            nameToCount.merge(name, 1, Long::plus)
        }
    }

    override fun getEventStatisticByName(name: String): EventStatistic.NamedEventStatistic? {
        cleanQueue()
        synchronized(this) {
            return getNamed(nameToCount, name)
        }
    }

    override fun getAllEventStatistic(): EventStatistic.SummaryEventStatistic {
        cleanQueue()
        synchronized(this) {
            return getAll(nameToCount)
        }
    }

    override fun printStatistic() {
        val nameToCount: Map<String, Long>
        synchronized(this) {
            nameToCount = this.nameToCount.toMap()
        }
        val all = getAll(nameToCount)
        all.asStringInfo().let(::println)
        all.nameToStatistic.values.forEach { println(it.asStringInfo()) }
    }

    private fun getNamed(map: Map<String, Long>, name: String): EventStatistic.NamedEventStatistic? {
        return map[name]?.let { count ->
            EventStatistic.NamedEventStatistic(
                name = name,
                requestsPerGranularity = count / divisor,
                granularity = granularity,
            )
        }
    }

    private fun getAll(map: Map<String, Long>): EventStatistic.SummaryEventStatistic {
        var sum = 0L
        val nameToStatistic = map.mapValues { (name, count) ->
            sum += count
            EventStatistic.NamedEventStatistic(
                name = name,
                requestsPerGranularity = count / divisor,
                granularity = granularity,
            )
        }
        return EventStatistic.SummaryEventStatistic(
            requestsPerGranularity = sum / divisor,
            granularity = granularity,
            nameToStatistic = nameToStatistic,
        )
    }

    private fun cleanQueue() {
        while (true) {
            synchronized(this) {
                val lastActual = clock.now() - interval
                repeat(100) { _ ->
                    eventQueue.firstOrNull()
                        ?.second
                        ?.takeIf { it < lastActual }
                        ?: return
                    val name = eventQueue.removeFirst().first
                    nameToCount.compute(name) { _, count -> count?.dec()?.takeIf { it != 0L } }
                }
            }
        }
    }

    private fun EventStatistic.asStringInfo(): String =
        when (this) {
            is EventStatistic.NamedEventStatistic ->
                "$requestsPerGranularity requests per ${granularity.name} for $name event"

            is EventStatistic.SummaryEventStatistic ->
                "$requestsPerGranularity requests per ${granularity.name} for"
        }
}
