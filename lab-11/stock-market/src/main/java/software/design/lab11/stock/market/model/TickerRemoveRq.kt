package software.design.lab11.stock.market.model

import kotlinx.serialization.Serializable

@Serializable
data class TickerRemoveRq(
    val code: String,
)
