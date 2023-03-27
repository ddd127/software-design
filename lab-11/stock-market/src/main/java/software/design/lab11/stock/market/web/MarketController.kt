package software.design.lab11.stock.market.web

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import software.design.lab11.stock.market.model.Ticker
import software.design.lab11.stock.market.model.TickerBuyRequest
import software.design.lab11.stock.market.model.TickerSellRequest

@RequestMapping(
    consumes = [MediaType.APPLICATION_JSON_VALUE],
    produces = [MediaType.APPLICATION_JSON_VALUE],
)
interface MarketController {

    @GetMapping("/ticker/get")
    @ResponseBody
    fun getTickers(): Map<String, Ticker>

    @GetMapping("/ticker/{code}/get")
    @ResponseBody
    fun getTicker(@PathVariable("code") code: String): Ticker

    @PostMapping("/ticker/buy")
    @ResponseBody
    fun buyTicker(@RequestBody rq: TickerBuyRequest)

    @PostMapping("/ticker/sell")
    @ResponseBody
    fun sellTicker(@RequestBody rq: TickerSellRequest)
}
