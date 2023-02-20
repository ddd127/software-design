package software.design.lab7.profiler

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties("aspect.profiler")
data class ProfilerProperties @ConstructorBinding constructor(
    val loggingPeriodMills: Long?,
)
