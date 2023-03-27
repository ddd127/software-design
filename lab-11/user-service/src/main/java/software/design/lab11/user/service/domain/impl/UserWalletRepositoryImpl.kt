package software.design.lab11.user.service.domain.impl

import org.springframework.stereotype.Repository
import software.design.lab11.user.service.domain.UserWalletRepository
import software.design.lab11.user.service.model.UserWallet
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

@Repository
class UserWalletRepositoryImpl : UserWalletRepository {

    private val userToBalance: ConcurrentMap<Long, Long> = ConcurrentHashMap()

    override fun createWallet(userId: Long, startAmount: Long): UserWallet {
        val result = userToBalance.putIfAbsent(userId, startAmount)
        if (result != null) {
            throw IllegalArgumentException("Wallet for given user already exists")
        }
        return UserWallet(userId, startAmount)
    }

    override fun getWallet(userId: Long): UserWallet {
        return userToBalance[userId]?.let { UserWallet(userId, it) }
            ?: throw NoSuchElementException("Not found wallet for given user")
    }

    override fun deposit(userId: Long, amount: Long): UserWallet {
        return userToBalance.computeIfPresent(userId) { _, balance ->
            balance + amount
        }?.let { UserWallet(userId, it) }
            ?: throw NoSuchElementException("Not found wallet for given user")
    }

    override fun withdraw(userId: Long, amount: Long): UserWallet {
        return userToBalance.computeIfPresent(userId) { _, balance ->
            (balance - amount).also {
                if (it < 0L) throw IllegalArgumentException("Can't withdraw more than balance")
            }
        }?.let { UserWallet(userId, it) }
            ?: throw NoSuchElementException("Not found wallet for given user")
    }
}
