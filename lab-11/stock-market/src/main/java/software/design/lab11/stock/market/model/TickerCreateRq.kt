package software.design.lab11.stock.market.model

import kotlinx.serialization.Serializable

@Serializable
data class TickerCreateRq(
    val code: String,
    val price: Long,
    val count: Long,
)
