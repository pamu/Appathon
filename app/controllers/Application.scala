package controllers

import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.EventSource
import play.api.libs.iteratee.Enumerator
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future

object Application extends Controller {
  def index = Action {
    Ok(views.html.index("Apptitude 2015"))
  }

  case class ContactUs(name: String, email: String, message: String)
  val contactUsForm = Form (
    mapping(
      "name" -> nonEmptyText,
      "email" -> email,
      "message" -> nonEmptyText
    )(ContactUs.apply)(ContactUs.unapply)
  )

  def contact() = Action { implicit request =>
    contactUsForm.bindFromRequest().fold(
      hasErrors => BadRequest(hasErrors),
      data => Status(200)
    )
  }
  
  def hits() = Action.async {
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
