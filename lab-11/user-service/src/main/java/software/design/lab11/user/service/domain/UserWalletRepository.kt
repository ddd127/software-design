package software.design.lab11.user.service.domain

import software.design.lab11.user.service.model.UserWallet

interface UserWalletRepository {
    fun createWallet(userId: Long, startAmount: Long): UserWallet
    fun getWallet(userId: Long): UserWallet
    fun deposit(userId: Long, amount: Long): UserWallet
    fun withdraw(userId: Long, amount: Long): UserWallet
}
