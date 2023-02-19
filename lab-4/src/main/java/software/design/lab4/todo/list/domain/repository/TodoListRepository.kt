package software.design.lab4.todo.list.domain.repository

import software.design.lab4.todo.list.domain.model.list.TodoList
import software.design.lab4.todo.list.domain.model.list.TodoListId
import software.design.lab4.todo.list.domain.model.user.UserId

interface TodoListRepository {
    fun createTodoList(rq: TodoList): TodoList
    fun getTodoListsByUser(userId: UserId): Set<TodoList>
    fun deleteTodoList(id: TodoListId)
}
