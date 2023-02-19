package software.design.lab4.todo.list.domain.repository.impl

import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import software.design.lab4.todo.list.domain.db.Tables.USERS
import software.design.lab4.todo.list.domain.db.tables.records.UsersRecord
import software.design.lab4.todo.list.domain.model.user.User
import software.design.lab4.todo.list.domain.model.user.UserId
import software.design.lab4.todo.list.domain.repository.UserRepository

@Repository
class UserRepositoryImpl @Autowired constructor(
    private val dslContext: DSLContext,
) : UserRepository {

    override fun createUser(rq: User): User =
        dslContext.insertInto(USERS)
            .set(
                toRecord(rq).apply {
                    reset(USERS.ID)
                },
            )
            .returning()
            .fetchSingle(::toModel)

    override fun getUser(id: UserId): User =
        dslContext.selectFrom(USERS)
            .where(USERS.ID.equal(id.value))
            .fetchSingle(::toModel)

    override fun getUser(login: String): User? =
        dslContext.selectFrom(USERS)
            .where(USERS.USER_LOGIN.equal(login))
            .fetchSingle(::toModel)

    companion object {

        private fun toRecord(model: User) =
            UsersRecord(
                model.id.value,
                model.login,
                model.name,
            )

        private fun toModel(record: UsersRecord) =
            User(
                UserId(record.id),
                record.userLogin,
                record.userName,
            )
    }
}
