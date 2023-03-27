package software.design.lab11.user.serivce

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import software.design.lab11.stock.market.model.TickerCreateRq
import software.design.lab11.stock.market.model.TickerRemoveRq
import software.design.lab11.user.serivce.TestConfiguration.Companion.adminController
import software.design.lab11.user.service.Application
import software.design.lab11.user.service.model.StockBuyRequest
import software.design.lab11.user.service.model.StockInfo
import software.design.lab11.user.service.model.StockSellRequest
import software.design.lab11.user.service.model.UserStocks
import software.design.lab11.user.service.web.AccountController
import software.design.lab11.user.service.web.StockController
import software.design.lab11.user.service.web.UserController
import kotlin.test.assertEquals

@SpringBootTest(classes = [TestConfiguration::class, Application::class])
class TestStockController {

    @Autowired
    private lateinit var accountController: AccountController

    @Autowired
    private lateinit var stockController: StockController

    @Autowired
    private lateinit var userController: UserController

    @BeforeEach
    fun init() {
        try {
            userController.delete(LOGIN)
        } catch (ignored: Exception) {
        }
        userController.register(LOGIN)

        try {
            adminController.removeTicker(TickerRemoveRq(stock1.code))
            adminController.removeTicker(TickerRemoveRq(stock2.code))
            adminController.removeTicker(TickerRemoveRq(stock3.code))
        } catch (ignored: Exception) {
        }
        adminController.createTicker(stock1)
        adminController.createTicker(stock2)
        adminController.createTicker(stock3)

        accountController.deposit(LOGIN, 10_000)
    }

    @Test
    fun testBuyAndSellStocks() {
        var initial = accountController.account(LOGIN)

        stockController.buyStocks(LOGIN, StockBuyRequest(stock1.code, 5))

        assertEquals(
            initial.copy(
                balance = initial.balance - stock1.price * 5,
                userStocks = UserStocks(
                    initial.userStocks.stocks +
                            mapOf(stock1.code to StockInfo(stock1.code, 5)),
                ),
                codeToPrice = initial.codeToPrice +
                        mapOf(stock1.code to stock1.price)
            ).also { initial = it },
            accountController.account(LOGIN)
        )

        stockController.buyStocks(LOGIN, StockBuyRequest(stock2.code, 20))
        stockController.buyStocks(LOGIN, StockBuyRequest(stock3.code, 50))

        assertEquals(
            initial.copy(
                balance = initial.balance - stock2.price * 20 - stock3.price * 50,
                userStocks = UserStocks(
                    initial.userStocks.stocks +
                            mapOf(
                                stock2.code to StockInfo(stock2.code, 20),
                                stock3.code to StockInfo(stock3.code, 50),
                            ),
                ),
                codeToPrice = initial.codeToPrice +
                        mapOf(
                            stock2.code to stock2.price,
                            stock3.code to stock3.price,
                        ),
            ).also { initial = it },
            accountController.account(LOGIN)
        )

        stockController.sellStocks(LOGIN, StockSellRequest(stock2.code, 20))
        stockController.sellStocks(LOGIN, StockSellRequest(stock1.code, 3))

        assertEquals(
            initial.copy(
                balance = initial.balance + stock2.price * 20 + stock1.price * 3,
                userStocks = UserStocks(
                    initial.userStocks.stocks +
                            mapOf(
                                stock1.code to StockInfo(stock1.code, 2),
                            ) - stock2.code,
                ),
                codeToPrice = initial.codeToPrice - stock2.code,
            ).also { initial = it },
            accountController.account(LOGIN)
        )
    }

    companion object {

        private const val LOGIN: String = "test-user"

        private val stock1 = TickerCreateRq("ticker-1", 10, 1000)
        private val stock2 = TickerCreateRq("ticker-2", 20, 1000)
        private val stock3 = TickerCreateRq("ticker-3", 50, 1000)

        @DynamicPropertySource
        @JvmStatic
        fun stockMarketProperties(registry: DynamicPropertyRegistry) {
            registry.add("stock-market.url") {
                "http://localhost:${TestConfiguration.stockMarketContainer.firstMappedPort}"
            }
        }
    }
}
