package controllers

import play.api.mvc.{Action, Controller}

object Application extends Controller {
  def index = Action {
    Ok(views.html.index("Welcome to Apptitude"))
  }
  def contact() = Action {
    Redirect(routes.Application.index()) withSession("contact" -> "success")
  }
}