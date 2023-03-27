package software.design.lab11.user.service.web.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController
import software.design.lab11.user.service.application.StockService
import software.design.lab11.user.service.model.StockBuyRequest
import software.design.lab11.user.service.model.StockBuyResult
import software.design.lab11.user.service.model.StockSellRequest
import software.design.lab11.user.service.model.StockSellResult
import software.design.lab11.user.service.web.StockController

@RestController
class StockControllerImpl @Autowired constructor(
    private val stockService: StockService,
) : StockController {

    override fun buyStocks(login: String, rq: StockBuyRequest): StockBuyResult {
        return stockService.buyStocks(login, rq)
    }

    override fun sellStocks(login: String, rq: StockSellRequest): StockSellResult {
        return stockService.sellStocks(login, rq)
    }
}
