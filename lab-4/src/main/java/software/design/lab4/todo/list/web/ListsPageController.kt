package software.design.lab4.todo.list.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.server.ResponseStatusException
import software.design.lab4.todo.list.domain.model.list.TodoList
import software.design.lab4.todo.list.domain.model.list.TodoListId
import software.design.lab4.todo.list.domain.model.user.User
import software.design.lab4.todo.list.domain.repository.TodoListRepository
import software.design.lab4.todo.list.domain.repository.UserRepository
import software.design.lab4.todo.list.web.dto.ListCreateRqDto

@Controller
class ListsPageController @Autowired constructor(
    private val userRepository: UserRepository,
    private val todoListRepository: TodoListRepository,
    @Value("\${controller.lists.page.default.login:ddd127}")
    private val defaultLogin: String,
) {

    @GetMapping("/lists")
    fun getListsPage(
        @RequestParam("login") loginParam: String?,
        model: Model,
    ): String {
        val user = userOrThrow(loginParam)
        val lists = todoListRepository.getTodoListsByUser(user.id).toList()
        model.addAttribute("login", user.login)
        model.addAttribute("taskList", lists)
        model.addAttribute("listCreateRq", ListCreateRqDto(""))
        return "lists"
    }

    @PostMapping("/lists/create")
    fun createList(
        @RequestParam("loginParam", required = false, defaultValue = "ddd127") loginParam: String?,
        @ModelAttribute listCreateRq: ListCreateRqDto,
        model: Model,
    ): String {
        val user = userOrThrow(loginParam)
        todoListRepository.createTodoList(TodoList(userId = user.id, title = listCreateRq.title))
        return getListsPage(loginParam, model)
    }

    @PostMapping("/lists/delete")
    fun deleteList(
        @RequestParam("loginParam", required = false, defaultValue = "ddd127") loginParam: String?,
        @RequestParam("todoListId") listIdParam: Long,
        model: Model,
    ): String {
        todoListRepository.deleteTodoList(TodoListId(listIdParam))
        return getListsPage(loginParam, model)
    }

    private fun userOrThrow(loginParam: String?): User =
        userRepository.getUser(loginParam ?: defaultLogin)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
}
