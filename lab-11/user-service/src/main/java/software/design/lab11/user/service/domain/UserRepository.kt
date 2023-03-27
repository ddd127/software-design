package software.design.lab11.user.service.domain

import software.design.lab11.user.service.model.User

interface UserRepository {
    fun createUser(login: String): User
    fun getUser(login: String): User
    fun deleteUser(login: String): User
}
