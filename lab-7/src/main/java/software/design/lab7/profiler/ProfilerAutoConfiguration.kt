package software.design.lab7.profiler

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import software.design.lab7.profiler.api.ProfileAggregator
import software.design.lab7.profiler.aspect.AspectProfiler
import software.design.lab7.profiler.impl.LoggingProfileAggregator
import java.time.Duration

@SpringBootConfiguration
@ConditionalOnProperty(
    "aspect.profiler.enabled",
    havingValue = "true",
    matchIfMissing = true,
)
@EnableConfigurationProperties(ProfilerProperties::class)
class ProfilerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun aspectProfiler(
        aggregators: List<ProfileAggregator>,
    ): AspectProfiler {
        return AspectProfiler(aggregators)
    }

    @Bean
    @ConditionalOnProperty(
        "aspect.profiler.logging.aggregator.enabled",
        havingValue = "true",
        matchIfMissing = true,
    )
    fun loggingProfileAggregator(
        properties: ProfilerProperties,
    ): ProfileAggregator {
        return LoggingProfileAggregator(
            Duration.ofMillis(properties.loggingPeriodMills ?: 2000),
        )
    }
}
