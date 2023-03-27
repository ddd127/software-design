package software.design.lab11.user.service

import feign.Feign
import feign.spring.SpringContract
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import software.design.lab11.stock.market.web.MarketController

@SpringBootApplication
class Application {

    @Bean
    fun marketController(
        @Value("\${stock-market.url}") marketUrl: String,
    ): MarketController {
        return Feign.builder()
            .contract(SpringContract())
            .encoder(Util.KotlinxEncoder)
            .decoder(Util.KotlinxDecoder)
            .target(
                MarketController::class.java,
                marketUrl,
            )
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
