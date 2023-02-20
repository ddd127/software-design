package software.design.lab4.todo.list.domain.model.list

import software.design.lab4.todo.list.domain.model.IdType

data class TodoListId(override val value: Long) : IdType
