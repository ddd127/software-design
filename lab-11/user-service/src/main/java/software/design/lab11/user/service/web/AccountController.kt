package software.design.lab11.user.service.web

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import software.design.lab11.user.service.model.AccountInfo

@RequestMapping(
    consumes = [MediaType.APPLICATION_JSON_VALUE],
    produces = [MediaType.APPLICATION_JSON_VALUE],
)
interface AccountController {

    @GetMapping("/account")
    @ResponseBody
    fun account(@RequestHeader login: String): AccountInfo

    @PostMapping("/deposit")
    fun deposit(
        @RequestHeader login: String,
        @RequestBody amount: Long,
    ): AccountInfo

    @PostMapping("/withdraw")
    fun withdraw(
        @RequestHeader login: String,
        @RequestBody amount: Long,
    ): AccountInfo
}
