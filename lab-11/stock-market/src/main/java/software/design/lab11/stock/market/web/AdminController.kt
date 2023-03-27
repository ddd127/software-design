package software.design.lab11.stock.market.web

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import software.design.lab11.stock.market.model.Ticker
import software.design.lab11.stock.market.model.TickerCreateRq
import software.design.lab11.stock.market.model.TickerRemoveRq
import software.design.lab11.stock.market.model.TickerUpdateRq

@RequestMapping(
    consumes = [MediaType.APPLICATION_JSON_VALUE],
    produces = [MediaType.APPLICATION_JSON_VALUE],
)
interface AdminController {

    @DeleteMapping("/admin/remove")
    @ResponseBody
    fun removeTicker(@RequestBody rq: TickerRemoveRq): Ticker?

    @PostMapping("/admin/create")
    @ResponseBody
    fun createTicker(@RequestBody rq: TickerCreateRq): Ticker

    @PutMapping("/admin/update")
    @ResponseBody
    fun updateTickerPrice(@RequestBody rq: TickerUpdateRq): Ticker?
}
