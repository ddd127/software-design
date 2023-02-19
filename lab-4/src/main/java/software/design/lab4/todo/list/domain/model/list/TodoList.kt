package software.design.lab4.todo.list.domain.model.list

import software.design.lab4.todo.list.domain.model.user.UserId
import java.time.Instant

data class TodoList(
    val id: TodoListId,
    val userId: UserId,
    val title: String,
    val createdTs: Instant,
    val updatedTs: Instant,
)
