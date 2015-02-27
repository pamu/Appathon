#Reminders schema

# --- !Ups

create table "reminders" (
    "userId" BIGINT NOT NULL,
    "status" BOOLEAN DEFAULT false NOT NULL,
    "id" BIGSERIAL NOT NULL PRIMARY KEY)

# --- !Downs

drop table "reminders"

# --- !Ups

alter table "reminders" add constraint "user_reminder_userId_fk" foreign key("userId") references "users"("id") on update NO ACTION on delete NO ACTION




