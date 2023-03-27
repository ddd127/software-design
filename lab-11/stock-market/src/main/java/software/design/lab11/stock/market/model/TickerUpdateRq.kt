package software.design.lab11.stock.market.model

import kotlinx.serialization.Serializable

@Serializable
data class TickerUpdateRq(
    val code: String,
    val price: Long,
)
