package controllers

import play.api.libs.EventSource
import play.api.libs.iteratee.Enumerator
import play.api.mvc.{Action, Controller}

object Application extends Controller {
  def index = Action {
    Ok(views.html.index("Apptitude 2015"))
  }
  def contact() = Action {
    Redirect(routes.Application.index()) withSession("contact" -> "success")
  }
  def hits() = Action {
    
    Ok.chunked(Enumerator[String]() &> EventSource()).as(EVENT_STREAM)
  }
}
