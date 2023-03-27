package software.design.lab11.user.serivce

import feign.Feign
import feign.spring.SpringContract
import org.springframework.boot.test.context.TestConfiguration
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import software.design.lab11.stock.market.web.AdminController
import software.design.lab11.user.service.Util

@TestConfiguration
@Testcontainers
class TestConfiguration {

    companion object {

        val stockMarketContainer: GenericContainer<*>

        init {
            stockMarketContainer = GenericContainer(
                DockerImageName.parse("library/stock-market:1.0-SNAPSHOT")
            ).withExposedPorts(8080)
                .withSharedMemorySize(1 shl 28)
            stockMarketContainer.start()
        }

        val adminController: AdminController by lazy {
            Feign.builder()
                .contract(SpringContract())
                .encoder(Util.KotlinxEncoder)
                .decoder(Util.KotlinxDecoder)
                .target(
                    AdminController::class.java,
                    "http://localhost:${stockMarketContainer.firstMappedPort}",
                )
        }
    }
}
