package controllers

import javax.inject._
import play.api._
import play.api.mvc._

/**
  * Authentication controller
  */
@Singleton
class AuthController @Inject()(cc: ControllerComponents)
  extends AbstractController(cc) {

  def login = Action {
    implicit r: Request[AnyContent] =>
      r.session.get("username_session") match {
        case Some(_) => Redirect("/")
        case None => Ok(views.html.login())
      }
  }

  def register = Action {
    implicit r: Request[AnyContent] =>
      r.session.get("username_session") match {
        case Some(_) => Redirect("/")
        case None => Ok(views.html.register())
      }
  }

  def logout = Action {
    Redirect("/").withNewSession
  }

  def backdoor = Action {
    Redirect("/").withSession("username_session" -> "foo")
  }

}
