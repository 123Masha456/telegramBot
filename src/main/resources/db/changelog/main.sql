-- liquibase formatted sql

-- changeset a:1

create table notification_task (
id serial primary key,
chat_id serial not null,
message varchar(100) not null,
local_date_time  timestamp without time zone not null);