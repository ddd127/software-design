package software.design.lab11.stock.market

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import software.design.lab11.stock.market.model.Ticker
import software.design.lab11.stock.market.model.TickerCreateRq
import software.design.lab11.stock.market.model.TickerUpdateRq
import software.design.lab11.stock.market.web.AdminController
import kotlin.test.assertEquals

@SpringBootTest
class AdminControllerTest {

    @Autowired
    private lateinit var adminController: AdminController

    @Test
    fun testCreation() {
        val rqs = (1..5).associate { index ->
            val rq = TickerCreateRq(
                code = "test-creation_$index",
                price = index * 10L,
                count = index * 200L,
            )
            rq to Ticker(rq.code, rq.price, rq.count)
        }
        val actual = rqs.map { (rq, expected) ->
            val actual = adminController.createTicker(rq)
            assertEquals(expected, actual)
            actual
        }.toSet()
        assertEquals(rqs.values.toSet(), actual)
    }

    @Test
    fun testUpdate() {
        val ticker = adminController.createTicker(
            TickerCreateRq(
                "test-update",
                10L,
                100L,
            )
        )
        val rqs = (1..5).map { index ->
            TickerUpdateRq(
                "test-update",
                index.toLong(),
            )
        }
        rqs.forEach { rq ->
            val expected = ticker.copy(price = rq.price)
            val actual = adminController.updateTickerPrice(rq)
            assertEquals(expected, actual)
        }
    }
}
