package software.design.lab11.stock.market.web.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController
import software.design.lab11.stock.market.domain.TickerRepository
import software.design.lab11.stock.market.model.Ticker
import software.design.lab11.stock.market.model.TickerCreateRq
import software.design.lab11.stock.market.model.TickerRemoveRq
import software.design.lab11.stock.market.model.TickerUpdateRq
import software.design.lab11.stock.market.web.AdminController

@RestController
internal class AdminControllerImpl @Autowired constructor(
    private val tickerRepository: TickerRepository,
) : AdminController {

    override fun removeTicker(rq: TickerRemoveRq): Ticker? {
        return tickerRepository.removeTicker(rq)
    }

    override fun createTicker(rq: TickerCreateRq): Ticker {
        return tickerRepository.createTicker(rq)
    }

    override fun updateTickerPrice(rq: TickerUpdateRq): Ticker? {
        return tickerRepository.updateTicker(rq)
    }
}
