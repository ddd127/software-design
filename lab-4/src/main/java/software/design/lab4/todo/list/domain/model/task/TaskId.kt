package software.design.lab4.todo.list.domain.model.task

import software.design.lab4.todo.list.domain.model.IdType

data class TaskId(override val value: Long) : IdType
