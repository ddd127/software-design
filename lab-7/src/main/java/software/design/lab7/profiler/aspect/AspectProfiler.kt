package software.design.lab7.profiler.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import software.design.lab7.profiler.api.ProfileAggregator
import java.time.Duration
import kotlin.system.measureNanoTime

@Aspect
class AspectProfiler(
    private val aggregators: List<ProfileAggregator>,
) {

    @Around("@annotation(software.design.lab7.profiler.api.Profiled)")
    fun logExecutionTime(joinPoint: ProceedingJoinPoint): Any? {
        return try {
            val result: Any?
            val duration = measureNanoTime {
                result = joinPoint.proceed()
            }.let(Duration::ofNanos)
            aggregators.forEach {
                it.process(joinPoint.target, joinPoint.signature, duration)
            }
            result
        } catch (t: Throwable) {
            throw t
        }
    }
}
