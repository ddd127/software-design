package software.design.lab11.stock.market.web.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController
import software.design.lab11.stock.market.domain.TickerRepository
import software.design.lab11.stock.market.model.Ticker
import software.design.lab11.stock.market.model.TickerBuyRequest
import software.design.lab11.stock.market.model.TickerBuyResult
import software.design.lab11.stock.market.model.TickerSellRequest
import software.design.lab11.stock.market.web.MarketController

@RestController
internal class MarketControllerImpl @Autowired constructor(
    private val repository: TickerRepository,
) : MarketController {

    override fun getTickers(): Map<String, Ticker> {
        return repository.getTickers()
    }

    override fun buyTicker(rq: TickerBuyRequest): TickerBuyResult {
        return repository.buyTickers(rq)
    }

    override fun sellTicker(rq: TickerSellRequest) {
        return repository.sellTickers(rq)
    }
}
