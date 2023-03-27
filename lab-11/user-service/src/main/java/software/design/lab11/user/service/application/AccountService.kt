package software.design.lab11.user.service.application

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import software.design.lab11.stock.market.web.MarketController
import software.design.lab11.user.service.domain.UserRepository
import software.design.lab11.user.service.domain.UserStockRepository
import software.design.lab11.user.service.domain.UserWalletRepository
import software.design.lab11.user.service.model.AccountInfo

@Service
class AccountService @Autowired constructor(
    private val marketController: MarketController,
    private val userRepository: UserRepository,
    private val userStockRepository: UserStockRepository,
    private val userWalletRepository: UserWalletRepository,
) {

    fun accountInfo(login: String): AccountInfo {
        val user = userRepository.getUser(login)
        val prices = marketController.getTickers()
        val balance = userWalletRepository.getWallet(user.id).balance
        val userStocks = userStockRepository.getStocks(user.id)

        return AccountInfo(
            login,
            balance,
            userStocks,
            prices.filterKeys { it in userStocks.stocks }
                .mapValues { (_, ticker) -> ticker.price },
        )
    }


    fun deposit(login: String, amount: Long): AccountInfo {
        val user = userRepository.getUser(login)
        userWalletRepository.deposit(user.id, amount)
        return accountInfo(login)
    }

    fun withdraw(login: String, amount: Long): AccountInfo {
        val user = userRepository.getUser(login)
        userWalletRepository.withdraw(user.id, amount)
        return accountInfo(login)
    }
}
