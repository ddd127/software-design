package software.design.lab11.user.service.model

import kotlinx.serialization.Serializable

@Serializable
data class UserWallet(
    val userId: Long,
    val balance: Long,
)
