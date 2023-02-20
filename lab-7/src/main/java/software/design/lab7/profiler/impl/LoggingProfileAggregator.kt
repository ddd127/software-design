package software.design.lab7.profiler.impl

import mu.KotlinLogging
import org.aspectj.lang.Signature
import software.design.lab7.profiler.api.ProfileAggregator
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.atomic.AtomicReference
import kotlin.concurrent.thread

class LoggingProfileAggregator(
    private val loggingPeriodDuration: Duration,
) : ProfileAggregator {

    private val loggingLambda: () -> Unit = {
        while (true) {
            Thread.sleep(loggingPeriodDuration)
            logger.debug("Starting to log measures")
            val count = signatureToStatistic.count { (signature, statistic) ->
                statistic.run {
                    logger.debug {
                        "Profiling    $signature    " +
                                "cnt = $count    " +
                                "min = $min    " +
                                "max = $max    " +
                                "sum = $sum    " +
                                "avg = $avg    "
                    }
                }
                true
            }
            logger.debug("Logged $count signatures")
        }
    }

    private val loggingThread: AtomicReference<Thread> =
        thread(isDaemon = true, start = true, block = loggingLambda).let(::AtomicReference)

    private val signatureToStatistic: ConcurrentMap<String, StatisticItem> = ConcurrentHashMap()

    override fun process(target: Any?, signature: Signature, duration: Duration) {
        signatureToStatistic.compute(signature.toLongString()) { _, prev ->
            prev?.plus(duration) ?: StatisticItem(duration)
        }
        loggingThread.getAndUpdate { current ->
            if (current.isAlive) {
                current
            } else {
                logger.warn("Logging thread is dead. Starting new.")
                thread(isDaemon = true, start = true, block = loggingLambda)
            }
        }
    }

    companion object {
        private val logger = KotlinLogging.logger { }
    }
}
