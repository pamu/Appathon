package models

import java.sql.Timestamp

/**
 * Created by android on 27/2/15.
 */

import scala.slick.driver.PostgresDriver.simple._

case class User(email: String, timestamp: Timestamp, id: Option[Long] = None)

class Users(tag: Tag) extends Table[User](tag, "users") {
  def email = column[String]("email", O.NotNull)
  def timestamp = column[Timestamp]("timestamp", O.NotNull)
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def * = (email, timestamp, id.?) <> (User.tupled, User.unapply)
}

case class Reminder(userId: Long, status: Boolean, id: Option[Long] = None)

class Reminders(tag: Tag) extends Table[Reminder](tag, "reminders") {
  def userId = column[Long]("userId", O.NotNull)
  def status = column[Boolean]("status", O.Default[Boolean](false))
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def * = (userId, status, id.?) <> (Reminder.tupled, Reminder.unapply)
  def userIdFk = foreignKey("user_reminder_userId_fk", userId, TableQuery[Users])(_.id)
}
