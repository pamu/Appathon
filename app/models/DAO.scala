package models

/**
 * Created by android on 27/2/15.
 */
import scala.slick.driver.PostgresDriver.simple._

object DAO {
  
  val users = TableQuery[Users]
  
  val reminders = TableQuery[Reminders]
  
  def db = Database.forURL(
    url = "postgres://lonctegzeyicvj:IGReZOVZd1_0nmOIVH4ElAVe9M@ec2-50-19-236-178." +
      "compute-1.amazonaws.com:5432/d439cvtcclgcrq", user = "lonctegzeyicvj",
    password = "IGReZOVZd1_0nmOIVH4ElAVe9M")
  
  def create(): Unit = {
    db.withSession(implicit session => {
      val stmts = users.ddl.createStatements.mkString
      println(stmts)
      val stmts1 = reminders.ddl.createStatements.mkString
      println(stmts1)
    })
  }
}
