package controllers

import play.api.libs.EventSource
import play.api.libs.iteratee.Enumerator
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.mvc.{Action, Controller}
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future
import scala.util.parsing.json.JSONObject

object Application extends Controller {
  def index = Action { implicit request =>
    import actors.CountingActor._
    import global._
    AppathonGlobal.counter ! Hit(request.remoteAddress)
    Ok(views.html.index("Apptitude 2015"))
  }

  case class ContactUs(name: String, email: String, message: String)
  
  implicit val reads: Reads[ContactUs] = (
      (JsPath \ "name").read[String] and
      (JsPath \ "email").read[String] and
      (JsPath \ "message").read[String]
    )(ContactUs.apply _)
  
  def contact() = Action(parse.json) { implicit request =>
    request.body.validate[ContactUs] match {
      case success: JsSuccess[ContactUs] => {
        val data = success.get
        import global._
        import actors.EmailActor._
        import contacts._
        AppathonGlobal.mailer ! MailContact(Constants.apptitudeEmail, s"${data.name} says", "Contact me @ "+data.email + " \n" + data.message)
        Ok(Json.obj("status" -> 200))
      }
      case e: JsError => {
        Status(BAD_REQUEST)
      }
    }
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
  }
}
