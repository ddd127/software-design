package software.design.lab11.user.service.application

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import software.design.lab11.stock.market.model.TickerBuyRequest
import software.design.lab11.stock.market.model.TickerSellRequest
import software.design.lab11.stock.market.web.MarketController
import software.design.lab11.user.service.domain.UserRepository
import software.design.lab11.user.service.domain.UserStockRepository
import software.design.lab11.user.service.domain.UserWalletRepository
import software.design.lab11.user.service.model.StockBuyRequest
import software.design.lab11.user.service.model.StockBuyResult
import software.design.lab11.user.service.model.StockInfo
import software.design.lab11.user.service.model.StockSellRequest
import software.design.lab11.user.service.model.StockSellResult

@Service
class StockService @Autowired constructor(
    private val marketController: MarketController,
    private val userRepository: UserRepository,
    private val userStockRepository: UserStockRepository,
    private val userWalletRepository: UserWalletRepository,
) {

    fun buyStocks(login: String, rq: StockBuyRequest): StockBuyResult {
        val user = userRepository.getUser(login)

        val stockInfo = StockInfo(rq.code, rq.count)
        val ticker = marketController.getTicker(rq.code)

        userStockRepository.addStock(user.id, stockInfo)
        userWalletRepository.withdraw(user.id, rq.count * ticker.price)

        try {
            marketController.buyTicker(
                TickerBuyRequest(rq.code, rq.count),
            )
        } catch (e: Exception) {
            userStockRepository.removeStock(user.id, stockInfo)
            userWalletRepository.deposit(user.id, rq.count * ticker.price)
            throw e
        }

        return StockBuyResult(
            rq.count,
            ticker.price,
        )
    }

    fun sellStocks(login: String, rq: StockSellRequest): StockSellResult {
        val user = userRepository.getUser(login)

        val stockInfo = StockInfo(rq.code, rq.count)
        val ticker = marketController.getTicker(rq.code)

        userStockRepository.removeStock(user.id, stockInfo)
        userWalletRepository.deposit(user.id, rq.count * ticker.price)

        try {
            marketController.sellTicker(
                TickerSellRequest(rq.code, rq.count),
            )
        } catch (e: Exception) {
            userStockRepository.addStock(user.id, stockInfo)
            userWalletRepository.withdraw(user.id, rq.count * ticker.price)
            throw e
        }

        return StockSellResult(
            rq.count,
            ticker.price,
        )
    }
}
