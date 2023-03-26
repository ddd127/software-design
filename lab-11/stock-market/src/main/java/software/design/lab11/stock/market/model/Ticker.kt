package software.design.lab11.stock.market.model

import kotlinx.serialization.Serializable

@Serializable
data class Ticker(
    val code: String,
    val price: Long,
    val count: Long,
)
