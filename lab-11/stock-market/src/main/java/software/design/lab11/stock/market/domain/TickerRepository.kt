package software.design.lab11.stock.market.domain

import software.design.lab11.stock.market.model.Ticker
import software.design.lab11.stock.market.model.TickerBuyRequest
import software.design.lab11.stock.market.model.TickerBuyResult
import software.design.lab11.stock.market.model.TickerCreateRq
import software.design.lab11.stock.market.model.TickerRemoveRq
import software.design.lab11.stock.market.model.TickerSellRequest
import software.design.lab11.stock.market.model.TickerUpdateRq

internal interface TickerRepository {
    fun createTicker(rq: TickerCreateRq): Ticker
    fun getTickers(): Map<String, Ticker>
    fun updateTicker(rq: TickerUpdateRq): Ticker?
    fun buyTickers(rq: TickerBuyRequest): TickerBuyResult
    fun sellTickers(rq: TickerSellRequest)
    fun removeTicker(rq: TickerRemoveRq): Ticker?
}
