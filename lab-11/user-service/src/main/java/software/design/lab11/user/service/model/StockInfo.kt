package software.design.lab11.user.service.model

import kotlinx.serialization.Serializable

@Serializable
data class StockInfo(
    val code: String,
    val count: Long,
)
