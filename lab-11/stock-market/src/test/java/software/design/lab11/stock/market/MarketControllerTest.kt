package software.design.lab11.stock.market

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import software.design.lab11.stock.market.model.Ticker
import software.design.lab11.stock.market.model.TickerBuyRequest
import software.design.lab11.stock.market.model.TickerCreateRq
import software.design.lab11.stock.market.model.TickerRemoveRq
import software.design.lab11.stock.market.model.TickerSellRequest
import software.design.lab11.stock.market.web.AdminController
import software.design.lab11.stock.market.web.MarketController
import java.lang.Exception
import kotlin.test.assertEquals

@SpringBootTest
class MarketControllerTest {

    @Autowired
    private lateinit var adminController: AdminController

    @Autowired
    private lateinit var marketController: MarketController

    @BeforeEach
    fun initTickers() {
        adminController.removeTicker(TickerRemoveRq(CODE))
        adminController.createTicker(
            TickerCreateRq(
                CODE,
                PRICE,
                INITIAL_COUNT,
            )
        )
    }

    @Test
    fun testBuy() {
        (1..5).forEach { index ->
            marketController.buyTicker(TickerBuyRequest(CODE, index.toLong()))
        }
        assertThrows<Exception> {
            marketController.buyTicker(TickerBuyRequest(CODE, INITIAL_COUNT * 2))
        }
        assertEquals(
            Ticker(CODE, PRICE, INITIAL_COUNT - (1..5).sum()),
            marketController.getTickers()[CODE],
        )
    }

    @Test
    fun testSell() {
        (1..5).forEach { index ->
            marketController.sellTicker(TickerSellRequest(CODE, index.toLong()))
        }
        marketController.sellTicker(TickerSellRequest(CODE, INITIAL_COUNT))
        assertEquals(
            Ticker(CODE, PRICE, INITIAL_COUNT * 2 + (1..5).sum()),
            marketController.getTickers()[CODE],
        )
    }

    companion object {
        private const val CODE = "market_test"
        private const val PRICE = 10L
        private const val INITIAL_COUNT = 100L
    }
}
