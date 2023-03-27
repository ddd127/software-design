package software.design.lab11.user.service.web.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController
import software.design.lab11.user.service.application.AccountService
import software.design.lab11.user.service.model.AccountInfo
import software.design.lab11.user.service.web.AccountController

@RestController
class AccountControllerImpl @Autowired constructor(
    private val accountService: AccountService,
) : AccountController {

    override fun account(login: String): AccountInfo {
        return accountService.accountInfo(login)
    }

    override fun deposit(login: String, amount: Long): AccountInfo {
        return accountService.deposit(login, amount)
    }

    override fun withdraw(login: String, amount: Long): AccountInfo {
        return accountService.withdraw(login, amount)
    }
}