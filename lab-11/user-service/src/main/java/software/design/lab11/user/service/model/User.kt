package software.design.lab11.user.service.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    val login: String,
)
