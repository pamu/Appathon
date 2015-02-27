package models

/**
 * Created by android on 27/2/15.
 */
import scala.slick.driver.PostgresDriver.simple._
import java.net.URI

object DAO {
  
  val users = TableQuery[Users]
  
  val reminders = TableQuery[Reminders]
  
  val uri = new URI("postgres://lonctegzeyicvj:IGReZOVZd1_0nmOIVH4ElAVe9M@ec2-50-19-236-178." +
  "compute-1.amazonaws.com:5432/d439cvtcclgcrq")
  
  val username = uri.getUserInfo.split(":")(0)
  
  val password = uri.getUserInfo.split(":")(1)
  
  def db = Database.forURL(
    driver = "org.postgresql.Driver",
    url = "jdbc:postgresql://" + uri.getHost + ":" + uri.getPort + uri.getPath, user = username,
    password = password)
  
  def create(): Unit = {
    db.withSession(implicit session => {
      users.ddl.create
      reminders.ddl.create
    })
  }
}
