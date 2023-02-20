package software.design.lab4.todo.list.domain.repository.impl

import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import software.design.lab4.todo.list.domain.db.Tables.TODO_LISTS
import software.design.lab4.todo.list.domain.db.tables.records.TodoListsRecord
import software.design.lab4.todo.list.domain.model.list.TodoList
import software.design.lab4.todo.list.domain.model.list.TodoListId
import software.design.lab4.todo.list.domain.model.user.UserId
import software.design.lab4.todo.list.domain.repository.TodoListRepository
import java.time.LocalDateTime
import java.time.ZoneOffset

@Repository
class TodoListRepositoryImpl @Autowired constructor(
    private val dslContext: DSLContext,
) : TodoListRepository {

    override fun createTodoList(rq: TodoList): TodoList =
        dslContext.insertInto(TODO_LISTS)
            .set(
                toRecord(rq).apply {
                    reset(TODO_LISTS.ID)
                    reset(TODO_LISTS.CREATED_TS)
                    reset(TODO_LISTS.UPDATED_TS)
                },
            )
            .returning()
            .fetchSingle(::toModel)

    override fun getTodoList(todoListId: TodoListId): TodoList =
        dslContext.selectFrom(TODO_LISTS)
            .where(TODO_LISTS.ID.equal(todoListId.value))
            .fetchSingle(::toModel)

    override fun getTodoListsByUser(userId: UserId): Set<TodoList> =
        dslContext.selectFrom(TODO_LISTS)
            .where(TODO_LISTS.USER_ID.equal(userId.value))
            .fetchSet(::toModel)

    override fun deleteTodoList(id: TodoListId) {
        dslContext.deleteFrom(TODO_LISTS)
            .where(TODO_LISTS.ID.equal(id.value))
            .execute()
    }

    companion object {

        private fun toRecord(model: TodoList) =
            TodoListsRecord(
                model.id.value,
                model.userId.value,
                model.title,
                LocalDateTime.ofInstant(model.createdTs, ZoneOffset.UTC),
                LocalDateTime.ofInstant(model.updatedTs, ZoneOffset.UTC),
            )

        private fun toModel(record: TodoListsRecord) =
            TodoList(
                TodoListId(record.id),
                UserId(record.userId),
                record.title,
                record.createdTs.toInstant(ZoneOffset.UTC),
                record.updatedTs.toInstant(ZoneOffset.UTC),
            )
    }
}
