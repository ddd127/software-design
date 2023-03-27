package software.design.lab11.user.service.web.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController
import software.design.lab11.user.service.application.UserService
import software.design.lab11.user.service.model.User
import software.design.lab11.user.service.web.UserController

@RestController
class UserControllerImpl @Autowired constructor(
    private val userService: UserService,
) : UserController {

    override fun register(login: String): User {
        return userService.registerUser(login)
    }

    override fun delete(login: String) {
        userService.deleteUser(login)
    }
}
