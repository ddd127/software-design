package software.design.lab4.todo.list

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import software.design.lab7.profiler.ProfilerAutoConfiguration

@SpringBootApplication
@ImportAutoConfiguration(ProfilerAutoConfiguration::class)
class Application

fun main(args: Array<String>) {
    SpringApplication(Application::class.java).run(*args)
}
