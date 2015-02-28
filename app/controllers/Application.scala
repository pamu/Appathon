package controllers

import java.sql.Timestamp
import java.util.Date

import play.api.libs.EventSource
import play.api.libs.iteratee.Enumerator
import play.api.libs.json._
import play.api.mvc.{Action, Controller}
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future

object Application extends Controller {
  
  def index = Action { implicit request =>
    import actors.CountingActor._
    import global._
    AppathonGlobal.counter ! Hit(request.remoteAddress)
    Ok(views.html.index("APPtitude"))
  }

  case class ContactUs(name: String, email: String, message: String)

  def contact() = Action(parse.json) { request =>

    import play.api.libs.functional.syntax._
    
    implicit val reads: Reads[ContactUs] = (
      (JsPath \ "name").read[String] and
        (JsPath \ "email").read[String] and
        (JsPath \ "message").read[String]
      )(ContactUs.apply _)
    
    request.body.validate[ContactUs] match {
      case success: JsSuccess[ContactUs] => {
        val data = success.get
        import global._
        import actors.EmailActor._
        import constants._
        AppathonGlobal.mailer ! MailContact(Constants.apptitudeEmail, s"${data.name} says", "Contact me @ "+data.email + " \n" + data.message)
        Ok(Json.obj("status" -> 200))
      }
      case e: JsError => {
        Status(BAD_REQUEST).as("application/json")
      }
    }
  }
  
  case class Remind(email: String)
  
  def remind() = Action.async(parse.json) { request =>
    
    implicit val remindReads: Reads[Remind] = (
      (JsPath \ "email").read[String].map(email => Remind(email))
      )
    
    request.body.validate[Remind] match {
      case success: JsSuccess[Remind] => {
        val remindMe = success.get
        import global._
        import actors.EmailActor._
        import constants._
        import utils._
        Future {
          import models._
          if(DAO.userExists(remindMe.email)) {
            AppathonGlobal.mailer ! RegHtmlEmail(remindMe.email, Constants.apptitudeEmail, "Thanks for your interest :)", Utils.mailBody("Looks like you have already visited this place. Anyways, We will send you an remainder email, just after registrations are open."))
          }else {
            AppathonGlobal.mailer ! Email(remindMe.email, Constants.apptitudeEmail, "Thanks for your interest :)", Utils.mailBody("You will be reminded when the registrations open. Till then keep developing Apps."))
          }
          Ok(Json.obj("status" -> 200))
        }
      }
      case failure: JsError => {
        Future(Status(BAD_REQUEST).as("application/json"))
      }
    }
  }
  
  def rules() = Action {
    Ok(views.html.rules())
  }
  
  def hits() = Action.async {
    import global._
    import actors.CountingActor._
    import akka.pattern.ask
    import akka.util.Timeout
    import scala.concurrent.duration._
    implicit val timeout = Timeout(5 seconds)
    val future: Future[BigInt] = (AppathonGlobal.counter ? Hits).mapTo[BigInt]
    future.map(hits => Ok(hits.toString)).fallbackTo(Future(NotFound))
  }
  
  /**
  def hitsStream() = Action.async {
    import global._
    import actors._
    import akka.pattern.ask
    import akka.util.Timeout
    import scala.concurrent.duration._
    implicit val timeout = Timeout(5 seconds)

    val future: Future[Enumerator[String]] = (AppathonGlobal.counter ? CountingActor.Stream).mapTo[Enumerator[String]]

    future.map(enumerator => Ok.chunked(enumerator &> EventSource()).as(EVENT_STREAM)).
      fallbackTo(Future(NotFound))
  }**/
}
