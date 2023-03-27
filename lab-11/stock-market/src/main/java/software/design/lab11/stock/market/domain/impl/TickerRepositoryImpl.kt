package software.design.lab11.stock.market.domain.impl

import org.springframework.stereotype.Repository
import software.design.lab11.stock.market.domain.TickerRepository
import software.design.lab11.stock.market.model.Ticker
import software.design.lab11.stock.market.model.TickerBuyRequest
import software.design.lab11.stock.market.model.TickerCreateRq
import software.design.lab11.stock.market.model.TickerRemoveRq
import software.design.lab11.stock.market.model.TickerSellRequest
import software.design.lab11.stock.market.model.TickerUpdateRq
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

@Repository
internal class TickerRepositoryImpl : TickerRepository {

    private val tickers: ConcurrentMap<String, Ticker> = ConcurrentHashMap()

    override fun createTicker(rq: TickerCreateRq): Ticker {
        val ticker = Ticker(rq.code, rq.price, rq.count)
        return tickers.putIfAbsent(rq.code, ticker) ?: ticker
    }

    override fun getTickers(): Map<String, Ticker> = tickers.toMap()

    override fun getTicker(code: String): Ticker? = tickers[code]

    override fun updateTicker(rq: TickerUpdateRq): Ticker? {
        while (true) {
            val current = tickers[rq.code] ?: return null
            val new = current.copy(price = rq.price)
            if (tickers.replace(rq.code, current, new)) return new
        }
    }

    override fun buyTickers(rq: TickerBuyRequest) {
        val e = IllegalStateException("Failed to buy tickers")
        if (rq.count <= 0) throw e
        val current = tickers[rq.code] ?: throw e
        val new = current.copy(count = current.count - rq.count)
        if (new.count < 0) throw e
        tickers.replace(rq.code, current, new).takeIf { it } ?: throw e
    }

    override fun sellTickers(rq: TickerSellRequest) {
        while (true) {
            val current = tickers[rq.code] ?: return
            val new = current.copy(count = current.count + rq.count)
            if (tickers.replace(rq.code, current, new)) return
        }
    }

    override fun removeTicker(rq: TickerRemoveRq): Ticker? {
        return tickers.remove(rq.code)
    }
}
