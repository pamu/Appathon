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

object EmailActor {
  case class Email(from: String, to: String, subject: String, body: String)
}

class EmailActor extends Actor with ActorLogging {
  
  val mail = use[MailerPlugin].email
  
  import EmailActor._
  
  def receive = {
    
    case Email(from, to, subject, body) => {
      
      val mailFuture = Future {
        mail.setFrom(from)
        mail.setRecipient(to)
        mail.setSubject(subject)
        mail.send(body)
      }
      
      mailFuture pipeTo self
    }
    case Failure => {
      log.info("Sendinf email failed")
    }
    case Success => {
      log.info("Email sent")
    } 
  
  }
}
