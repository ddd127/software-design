package software.design.lab11.stock.market.model

data class TickerCreateRq(
    val code: String,
    val price: Long,
    val count: Long,
)
