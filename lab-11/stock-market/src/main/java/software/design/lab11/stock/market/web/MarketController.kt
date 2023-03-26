package software.design.lab11.stock.market.web

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import software.design.lab11.stock.market.model.Ticker
import software.design.lab11.stock.market.model.TickerBuyRequest
import software.design.lab11.stock.market.model.TickerBuyResult
import software.design.lab11.stock.market.model.TickerSellRequest

@RequestMapping(
    consumes = [MediaType.APPLICATION_JSON_VALUE],
    produces = [MediaType.APPLICATION_JSON_VALUE],
)
interface MarketController {

    @GetMapping("/ticker/get")
    fun getTickers(): Map<String, Ticker>

    @PostMapping("/ticker/buy")
    @ResponseBody
    fun buyTicker(rq: TickerBuyRequest): TickerBuyResult

    @GetMapping("/ticker/sell")
    fun sellTicker(rq: TickerSellRequest)
}
