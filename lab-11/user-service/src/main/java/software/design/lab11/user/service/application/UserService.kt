package software.design.lab11.user.service.application

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import software.design.lab11.user.service.domain.UserRepository
import software.design.lab11.user.service.domain.UserWalletRepository
import software.design.lab11.user.service.model.User

@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository,
    private val walletRepository: UserWalletRepository,
) {

    fun registerUser(login: String): User {
        val user = userRepository.createUser(login)
        walletRepository.createWallet(user.id, 0L)
        return user
    }

    fun deleteUser(login: String): User {
        return userRepository.deleteUser(login)
    }
}
