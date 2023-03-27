package software.design.lab11.user.service.web

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import software.design.lab11.user.service.model.User

@RequestMapping(
    consumes = [MediaType.APPLICATION_JSON_VALUE],
    produces = [MediaType.APPLICATION_JSON_VALUE],
)
interface UserController {

    @PostMapping("/register")
    @ResponseBody
    fun register(@RequestBody login: String): User

    @DeleteMapping("/delete")
    fun delete(@RequestHeader login: String)
}
