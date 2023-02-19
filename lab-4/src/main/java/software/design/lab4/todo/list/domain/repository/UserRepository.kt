package software.design.lab4.todo.list.domain.repository

import software.design.lab4.todo.list.domain.model.user.User
import software.design.lab4.todo.list.domain.model.user.UserId

interface UserRepository {
    fun createUser(rq: User): User
    fun getUser(id: UserId): User
    fun getUser(login: String): User?
}
