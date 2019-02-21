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
      Ok(views.html.login())
  }

  def register = Action {
    implicit r: Request[AnyContent] =>
      Ok(views.html.register())
  }

  def logout = Action {
    implicit r: Request[AnyContent] =>
      Ok("wip")
  }

}
