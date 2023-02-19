package software.design.lab4.todo.list.domain.model.user

import software.design.lab4.todo.list.domain.model.IdType

data class UserId(override val value: Long) : IdType
