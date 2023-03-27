package software.design.lab11.user.service.model

import kotlinx.serialization.Serializable

@Serializable
data class StockSellResult(
    val count: Long,
    val price: Long,
) {
    val totalPrice: Long = count * price
}
