package actors

import akka.actor.{ActorLogging, Actor}
import akka.actor.Status.{Success, Failure}
import scala.concurrent.Future

/**
 * Created by android on 24/2/15.
 */
import com.typesafe.plugin._
import akka.pattern.pipe
import play.api.Play.current

object EmailActor {
  case class MailContact(email: String, subject: String, body: String)
  case object MailedContact
  case class Email(to: String, from: String, subject: String, body: String)
  case class EmailSent(to: String)
  case class HtmlEmail(to: String, from: String, subject: String, htmlBody: String)
  case class HtmlEmailSent(to: String)
}

class EmailActor extends Actor with ActorLogging {
  
  import context.dispatcher
  
  val mail = use[MailerPlugin].email
  
  import EmailActor._
  
  def receive = {

    case MailContact(email, subject, body) => {
      log.info("got an email request from {}", email)

      val mailFuture = Future {
        mail.setFrom(email)
        mail.setRecipient(email)
        mail.setSubject(subject)
        mail.send(body)
        MailedContact
      }

      mailFuture pipeTo self
    }
      
    case Email(to, from, subject, body) => {
      log.info("got an email request from {}", from)

      val mailFuture = Future {
        mail.setFrom(from)
        mail.setRecipient(to)
        mail.setSubject(subject)
        mail.send(body)
        EmailSent(to)
      }

      mailFuture pipeTo self
    }
    case HtmlEmail(to, from, subject, htmlBody) => {
      log.info("got an email request from {}", from)

      val mailFuture = Future {
        mail.setFrom(from)
        mail.setRecipient(to)
        mail.setSubject(subject)
        mail.sendHtml(htmlBody)
        HtmlEmailSent(to)
      }

      mailFuture pipeTo self
    }

   
    case failure: Failure => {
      log.info("Sending email failed reason: {}", failure.toString)
    }
      
    case success: Success => {
      
      success.status match {
        
        case EmailSent(email) => {
          log.info("email sent to {}", email)
        }
        
        case HtmlEmailSent(email) => {
          log.info("email sent to {}", email)
        }
        
        case _ => log.info("unknown message")
      }
      
      log.info("Email sent :)")
    }
      
    case x => log.info("unknown message of type", x.getClass)

  }
}
