#Users schema

# --- !Ups

create table "users" (
    "email" VARCHAR(254) NOT NULL,
    "timestamp" TIMESTAMP NOT NULL,
    "id" BIGSERIAL NOT NULL PRIMARY KEY
    );

# --- !Downs

drop table "users";