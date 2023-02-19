package software.design.lab4.todo.list.domain.model.task

import software.design.lab4.todo.list.domain.model.list.TodoListId
import java.time.Instant

data class Task(
    val id: TaskId,
    val listId: TodoListId,
    val status: TaskStatus,
    val title: String,
    val description: String,
    val createdTs: Instant,
    val updatedTs: Instant,
)
