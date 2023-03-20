package software.design.lab10.events

import kotlinx.datetime.Clock
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {

    @Bean
    fun clock(): Clock = Clock.System
}

fun main(args: Array<String>) {
    SpringApplication(Application::class.java).run(*args)
}
