package software.design.lab11.stock.market.model

import kotlinx.serialization.Serializable

@Serializable
data class TickerBuyRequest(
    val code: String,
    val count: Long,
)
