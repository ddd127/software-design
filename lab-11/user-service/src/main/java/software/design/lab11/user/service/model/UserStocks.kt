package software.design.lab11.user.service.model

import kotlinx.serialization.Serializable

@Serializable
data class UserStocks(
    val stocks: Map<String, StockInfo>,
)
