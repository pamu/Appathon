package models

/**
 * Created by android on 27/2/15.
 */

import play.api.Logger

import scala.slick.driver.PostgresDriver.simple._
import java.net.URI

object DAO {
  
  val users = TableQuery[Users]
  
  val reminders = TableQuery[Reminders]
  
  val uri = new URI("postgres://lonctegzeyicvj:IGReZOVZd1_0nmOIVH4ElAVe9M@ec2-50-19-236-178." +
  "compute-1.amazonaws.com:5432/d439cvtcclgcrq")
  
  val username = uri.getUserInfo.split(":")(0)
  
  val password = uri.getUserInfo.split(":")(1)
  
  lazy val db = Database.forURL(
    driver = "org.postgresql.Driver",
    url = "jdbc:postgresql://" + uri.getHost + ":" + uri.getPort + uri.getPath, user = username,
    password = password)
  
  def createIfNotExists(): Unit = {
    db.withSession(implicit session => {
      import scala.slick.jdbc.meta._
      if(MTable.getTables("reminders").list.isEmpty) {
        (users.ddl ++ reminders.ddl).create
        Logger.info("tables created")
      }
    })
  }
  
  
  def userExists(email: String): Boolean = {
    db.withSession(implicit session => {
      val q = for(user <- users.filter(_.email === email)) yield user
      q.exists.run
    })
  }

  def save(user: User): Long = {
    db.withTransaction(implicit tx => {
      (users returning users.map(_.id)) += user
    })
  }
  
  def save(reminder: Reminder): Long = {
    db.withTransaction(implicit tx => {
      (reminders returning reminders.map(_.id)) += reminder
    })
  }
}
