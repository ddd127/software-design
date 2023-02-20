package software.design.lab4.todo.list.domain.model.task

import software.design.lab4.todo.list.domain.model.list.TodoListId
import java.time.Instant

data class Task(
    val id: TaskId = TaskId(0),
    val listId: TodoListId,
    val status: TaskStatus = TaskStatus.ACTIVE,
    val title: String,
    val description: String,
    val createdTs: Instant = Instant.now(),
    val updatedTs: Instant = Instant.now(),
)
