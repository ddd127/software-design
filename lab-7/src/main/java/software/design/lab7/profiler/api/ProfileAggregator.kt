package software.design.lab7.profiler.api

import org.aspectj.lang.Signature
import java.time.Duration

interface ProfileAggregator {

    /**
     * This method must be thread-safe
     */
    fun process(target: Any?, signature: Signature, duration: Duration)
}
