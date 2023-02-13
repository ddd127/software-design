create table users
(
    id         bigserial    not null,
    user_login varchar(128) not null,
    user_name  varchar(256) not null,

    constraint pk__users primary key (id),
    constraint udx__users__login unique (user_login)
);

insert into users(user_login, user_name)
values ('ddd127', 'Danil Demintsev');


create table lists
(
    id         bigserial    not null,
    user_id    bigint       not null,
    title      varchar(256) not null,
    created_ts timestamp    not null default now(),

    constraint pk__lists primary key (id),
    constraint fk__lists__user_id foreign key (user_id) references users (id)
);

create index idx__lists__user_id on lists using btree (user_id);


create type task_status_enum as enum ('ACTIVE', 'FINISHED');

create table todo_task
(
    id          bigserial        not null,
    list_id     bigint           not null,
    status      task_status_enum not null,
    title       varchar(128)     not null,
    description varchar(2048)    null,
    created_ts  timestamp        not null default now(),
    updated_ts  timestamp        not null default now(),

    constraint pk__todo_task primary key (id),
    constraint fk__todo_task__list_id foreign key (list_id) references lists
);

create index idx__todo_tasks__list_id on todo_task using btree (list_id);
