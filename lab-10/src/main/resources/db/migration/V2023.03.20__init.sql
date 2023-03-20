create sequence sq__ticket_events__ticket_id;
create sequence sq__ticket_events__event_id;

create table ticket_events
(
    event_id   bigint        not null,
    ticket_id  bigint        not null,
    event_data varchar(4096) not null,

    constraint pk__ticket_events primary key (event_id)
);


create index idx__ticket_events__ticket_id on ticket_events using btree (ticket_id);
