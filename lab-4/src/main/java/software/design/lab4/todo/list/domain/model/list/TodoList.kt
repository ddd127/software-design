package software.design.lab4.todo.list.domain.model.list

import software.design.lab4.todo.list.domain.model.user.UserId
import java.time.Instant

data class TodoList(
    val id: TodoListId = TodoListId(0),
    val userId: UserId,
    val title: String,
    val createdTs: Instant = Instant.now(),
    val updatedTs: Instant = createdTs,
)
