package software.design.lab11.user.service.web

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import software.design.lab11.user.service.model.StockBuyRequest
import software.design.lab11.user.service.model.StockBuyResult
import software.design.lab11.user.service.model.StockSellRequest
import software.design.lab11.user.service.model.StockSellResult

@RequestMapping(
    consumes = [MediaType.APPLICATION_JSON_VALUE],
    produces = [MediaType.APPLICATION_JSON_VALUE],
)
interface StockController {

    @PostMapping("/buy")
    @ResponseBody
    fun buyStocks(
        @RequestHeader login: String,
        @RequestBody rq: StockBuyRequest,
    ): StockBuyResult

    @PostMapping("/sell")
    @ResponseBody
    fun sellStocks(
        @RequestHeader login: String,
        @RequestBody rq: StockSellRequest,
    ): StockSellResult
}
