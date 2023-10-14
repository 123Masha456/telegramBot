-- liquibase formatted sql

-- changeset a:1

create table notification_task (
id serial primary key,
chat_id bigint not null,
date_time timestamp without time zone not null,
message varchar(100) not null);