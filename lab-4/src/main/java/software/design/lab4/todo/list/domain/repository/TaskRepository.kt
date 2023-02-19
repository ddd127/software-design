package software.design.lab4.todo.list.domain.repository

import software.design.lab4.todo.list.domain.model.list.TodoListId
import software.design.lab4.todo.list.domain.model.task.Task
import software.design.lab4.todo.list.domain.model.task.TaskId

interface TaskRepository {
    fun createTask(rq: Task): Task
    fun getTasks(id: TodoListId): Set<Task>
    fun updateTask(rq: Task): Task
    fun deleteTask(id: TaskId)
}
