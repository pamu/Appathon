package controllers

import play.api.mvc.{Action, Controller}

object Application extends Controller {
  def index = Action {
    Ok(views.html.index("Apptitude 2015"))
  }
  def contact() = Action {
    Redirect(routes.Application.index()) withSession("contact" -> "success")
  }
}
