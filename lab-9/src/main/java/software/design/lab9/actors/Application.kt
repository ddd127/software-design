package software.design.lab9.actors

import akka.actor.ActorSystem
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {

    @Bean
    fun searchActorSystem(): ActorSystem {
        return ActorSystem.create("SearchActors")
    }
}

fun main() {
    runApplication<Application>().start()
}
