package actors

import akka.actor.{ActorLogging, Actor}
import akka.actor.Status.{Success, Failure}

import scala.concurrent.Future

/**
 * Created by android on 24/2/15.
 */
import com.typesafe.plugin._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import akka.pattern.pipe
import play.api.Play.current

object EmailActor {
  case class Email(email: String, subject: String, body: String)
}

class EmailActor extends Actor with ActorLogging {
  
  val mail = use[MailerPlugin].email
  
  import EmailActor._
  
  def receive = {
    
    case Email(email, subject, body) => {
      
      log.info("got an email request from {}", email)

      val mailFuture = Future {
        mail.setFrom(email)
        mail.setRecipient(email)
        mail.setSubject(subject)
        mail.send(body)
      }

      mailFuture pipeTo self
    }
    case failure: Failure => {
      log.info("Sending email failed reason: {}", failure.cause)
    }
    case Success => {
      log.info("Email sent :)")
    } 
  
  }
}
