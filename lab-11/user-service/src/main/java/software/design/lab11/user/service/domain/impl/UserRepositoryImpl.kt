package software.design.lab11.user.service.domain.impl

import org.springframework.stereotype.Repository
import software.design.lab11.user.service.domain.UserRepository
import software.design.lab11.user.service.model.User
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.atomic.AtomicLong

@Repository
class UserRepositoryImpl : UserRepository {

    private val users: ConcurrentMap<String, User> = ConcurrentHashMap()

    override fun createUser(login: String): User {
        val user = User(idSequence.getAndIncrement(), login)
        if (users.putIfAbsent(login, user) == null) {
            return user
        } else {
            throw IllegalArgumentException("User with given login already exists")
        }
    }

    override fun getUser(login: String): User {
        return users[login]
            ?: throw NoSuchElementException("User with given login does not exists")
    }

    override fun deleteUser(login: String): User {
        return users.remove(login)
            ?: throw NoSuchElementException("User with given login does not exists")
    }

    companion object {
        private val idSequence = AtomicLong(1L)
    }
}
