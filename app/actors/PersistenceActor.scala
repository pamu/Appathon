package actors

import java.sql.Timestamp
import java.util.Date

import akka.actor.Status.Success
import akka.actor.{ActorLogging, Actor}

import scala.concurrent.Future
import scala.util.Failure
import akka.pattern.pipe

/**
 * Created by android on 2/3/15.
 */
object PersistenceActor {
  case class Email(email: String)
  case class Persist(email: Email)
}

class PersistenceActor extends Actor with ActorLogging {
  import PersistenceActor._
  
  override def receive = {
    case Persist(email) => {
      import context.dispatcher
      Future {
        import models._
        val id = DAO.save(User(email.email, new Timestamp(new Date().getTime)))
        DAO.save(Reminder(id))
      } pipeTo self
    }
    case success: Success => log.info("future success")
    case Failure => log.info("future failed")
    case x => log.info("unknown message {}", x.getClass)
  }
}
