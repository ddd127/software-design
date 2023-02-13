/*
 * This file is generated by jOOQ.
 */
package software.design.lab4.todo.list.domain.db.tables;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function7;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row7;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import software.design.lab4.todo.list.domain.db.Indexes;
import software.design.lab4.todo.list.domain.db.Keys;
import software.design.lab4.todo.list.domain.db.Public;
import software.design.lab4.todo.list.domain.db.enums.TaskStatusEnum;
import software.design.lab4.todo.list.domain.db.tables.records.TodoTaskRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TodoTask extends TableImpl<TodoTaskRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.todo_task</code>
     */
    public static final TodoTask TODO_TASK = new TodoTask();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TodoTaskRecord> getRecordType() {
        return TodoTaskRecord.class;
    }

    /**
     * The column <code>public.todo_task.id</code>.
     */
    public final TableField<TodoTaskRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.todo_task.list_id</code>.
     */
    public final TableField<TodoTaskRecord, Long> LIST_ID = createField(DSL.name("list_id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.todo_task.status</code>.
     */
    public final TableField<TodoTaskRecord, TaskStatusEnum> STATUS = createField(DSL.name("status"), SQLDataType.VARCHAR.nullable(false).asEnumDataType(software.design.lab4.todo.list.domain.db.enums.TaskStatusEnum.class), this, "");

    /**
     * The column <code>public.todo_task.title</code>.
     */
    public final TableField<TodoTaskRecord, String> TITLE = createField(DSL.name("title"), SQLDataType.VARCHAR(128).nullable(false), this, "");

    /**
     * The column <code>public.todo_task.description</code>.
     */
    public final TableField<TodoTaskRecord, String> DESCRIPTION = createField(DSL.name("description"), SQLDataType.VARCHAR(2048), this, "");

    /**
     * The column <code>public.todo_task.created_ts</code>.
     */
    public final TableField<TodoTaskRecord, LocalDateTime> CREATED_TS = createField(DSL.name("created_ts"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field("now()", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>public.todo_task.updated_ts</code>.
     */
    public final TableField<TodoTaskRecord, LocalDateTime> UPDATED_TS = createField(DSL.name("updated_ts"), SQLDataType.LOCALDATETIME(6).nullable(false).defaultValue(DSL.field("now()", SQLDataType.LOCALDATETIME)), this, "");

    private TodoTask(Name alias, Table<TodoTaskRecord> aliased) {
        this(alias, aliased, null);
    }

    private TodoTask(Name alias, Table<TodoTaskRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.todo_task</code> table reference
     */
    public TodoTask(String alias) {
        this(DSL.name(alias), TODO_TASK);
    }

    /**
     * Create an aliased <code>public.todo_task</code> table reference
     */
    public TodoTask(Name alias) {
        this(alias, TODO_TASK);
    }

    /**
     * Create a <code>public.todo_task</code> table reference
     */
    public TodoTask() {
        this(DSL.name("todo_task"), null);
    }

    public <O extends Record> TodoTask(Table<O> child, ForeignKey<O, TodoTaskRecord> key) {
        super(child, key, TODO_TASK);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.IDX__TODO_TASKS__LIST_ID);
    }

    @Override
    public Identity<TodoTaskRecord, Long> getIdentity() {
        return (Identity<TodoTaskRecord, Long>) super.getIdentity();
    }

    @Override
    public UniqueKey<TodoTaskRecord> getPrimaryKey() {
        return Keys.PK__TODO_TASK;
    }

    @Override
    public List<ForeignKey<TodoTaskRecord, ?>> getReferences() {
        return Arrays.asList(Keys.TODO_TASK__FK__TODO_TASK__LIST_ID);
    }

    private transient Lists _lists;

    /**
     * Get the implicit join path to the <code>public.lists</code> table.
     */
    public Lists lists() {
        if (_lists == null)
            _lists = new Lists(this, Keys.TODO_TASK__FK__TODO_TASK__LIST_ID);

        return _lists;
    }

    @Override
    public TodoTask as(String alias) {
        return new TodoTask(DSL.name(alias), this);
    }

    @Override
    public TodoTask as(Name alias) {
        return new TodoTask(alias, this);
    }

    @Override
    public TodoTask as(Table<?> alias) {
        return new TodoTask(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public TodoTask rename(String name) {
        return new TodoTask(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TodoTask rename(Name name) {
        return new TodoTask(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public TodoTask rename(Table<?> name) {
        return new TodoTask(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<Long, Long, TaskStatusEnum, String, String, LocalDateTime, LocalDateTime> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function7<? super Long, ? super Long, ? super TaskStatusEnum, ? super String, ? super String, ? super LocalDateTime, ? super LocalDateTime, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function7<? super Long, ? super Long, ? super TaskStatusEnum, ? super String, ? super String, ? super LocalDateTime, ? super LocalDateTime, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
