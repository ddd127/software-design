package software.design.lab4.todo.list.domain.repository.impl

import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import software.design.lab4.todo.list.domain.db.Tables.TASKS
import software.design.lab4.todo.list.domain.db.enums.TaskStatusEnum
import software.design.lab4.todo.list.domain.db.tables.records.TasksRecord
import software.design.lab4.todo.list.domain.model.list.TodoListId
import software.design.lab4.todo.list.domain.model.task.Task
import software.design.lab4.todo.list.domain.model.task.TaskId
import software.design.lab4.todo.list.domain.model.task.TaskStatus
import software.design.lab4.todo.list.domain.repository.TaskRepository
import java.time.LocalDateTime
import java.time.ZoneOffset

@Repository
class TaskRepositoryImpl @Autowired constructor(
    private val dslContext: DSLContext,
) : TaskRepository {

    override fun createTask(rq: Task): Task =
        dslContext.insertInto(TASKS)
            .set(
                toRecord(rq).apply {
                    reset(TASKS.ID)
                    reset(TASKS.CREATED_TS)
                    reset(TASKS.UPDATED_TS)
                },
            )
            .returning()
            .fetchSingle(::toModel)

    override fun getTask(id: TaskId): Task =
        dslContext.selectFrom(TASKS)
            .where(TASKS.ID.equal(id.value))
            .fetchSingle(::toModel)

    override fun getTasks(id: TodoListId): Set<Task> =
        dslContext.selectFrom(TASKS)
            .where(TASKS.LIST_ID.equal(id.value))
            .fetchSet(::toModel)

    override fun updateTask(rq: Task): Task =
        dslContext.update(TASKS)
            .set(toRecord(rq))
            .set(TASKS.UPDATED_TS, DSL.now().cast(LocalDateTime::class.java))
            .where(TASKS.ID.equal(rq.id.value))
            .returning()
            .fetchSingle(::toModel)

    override fun deleteTask(id: TaskId): Task =
        dslContext.deleteFrom(TASKS)
            .where(TASKS.ID.equal(id.value))
            .returning()
            .fetchSingle(::toModel)

    companion object {

        private fun toRecord(model: Task) =
            TasksRecord(
                model.id.value,
                model.listId.value,
                toRecord(model.status),
                model.title,
                model.description,
                LocalDateTime.ofInstant(model.createdTs, ZoneOffset.UTC),
                LocalDateTime.ofInstant(model.updatedTs, ZoneOffset.UTC),
            )

        private fun toRecord(model: TaskStatus): TaskStatusEnum =
            when (model) {
                TaskStatus.ACTIVE -> TaskStatusEnum.ACTIVE
                TaskStatus.FINISHED -> TaskStatusEnum.FINISHED
            }

        private fun toModel(record: TasksRecord) =
            Task(
                TaskId(record.id),
                TodoListId(record.listId),
                toModel(record.status),
                record.title,
                record.description,
                record.createdTs.toInstant(ZoneOffset.UTC),
                record.updatedTs.toInstant(ZoneOffset.UTC),
            )

        private fun toModel(record: TaskStatusEnum): TaskStatus =
            when (record) {
                TaskStatusEnum.ACTIVE -> TaskStatus.ACTIVE
                TaskStatusEnum.FINISHED -> TaskStatus.FINISHED
            }
    }
}
