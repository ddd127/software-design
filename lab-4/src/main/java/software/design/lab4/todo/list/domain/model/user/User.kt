package software.design.lab4.todo.list.domain.model.user

data class User(
    val id: UserId,
    val login: String,
    val name: String,
)
