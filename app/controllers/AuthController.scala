package controllers

import javax.inject._
import models.{LoginForm, RegisterForm}
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

/**
  * Authentication controller
  */
@Singleton
class AuthController @Inject()(cc: MessagesControllerComponents)
  extends MessagesAbstractController(cc) {

  private lazy val SESSION_KEY = "username_session"

  val loginForm: Form[LoginForm] = Form (
    mapping(
      "username" -> nonEmptyText,//.verifying(x => x.matches("[0-9a-zA-Z]*")),
      "password" -> nonEmptyText//.verifying(x => x.length < 20)
    )(LoginForm.apply)(LoginForm.unapply)
  )

  val registerForm: Form[RegisterForm] = Form (
    mapping(
      "username" -> nonEmptyText,//.verifying(x => x.matches("[0-9a-zA-Z]*")),
      "password" -> nonEmptyText,//.verifying(x => x.length < 20),
      "password2"-> nonEmptyText,//.verifying(x => x.length < 20),
      "pubkey"   -> nonEmptyText
    )(RegisterForm.apply)(RegisterForm.unapply)
  )

  private def simpleLogin(username: String, password: String) = {

    lazy val creds = Map("admin" -> "admin")

    /**
      * Strong security
      */
    creds.exists(_ == (username, password))
  }

  def login = Action {
    implicit r: MessagesRequest[AnyContent] =>
      r.session.get(SESSION_KEY) match {
        case Some(_) => Redirect("/")
          .flashing("info" -> "You are already logged in.")
        case None =>
          Ok(views.html.login(loginForm, routes.AuthController.dologin()))
      }
  }

  def dologin = Action { implicit request: MessagesRequest[AnyContent] =>

      val error = { e: Form[LoginForm] =>
        BadRequest(views.html.login(e, routes.AuthController.dologin()))
      }

      val success = { x: LoginForm =>
      //  if (simpleLogin(x.username, x.password)) {
        if (true) {
          Redirect(routes.HomeController.index())
            .flashing("info" -> "You are now logged in.")
            .withSession(SESSION_KEY -> x.username)
        } else {
          Redirect(routes.AuthController.login())
            .flashing("error" -> "Invalid username/password.")
        }
      }
      loginForm.bindFromRequest.fold(error, success)
  }

  def register = Action {
    implicit r: MessagesRequest[AnyContent] =>
      r.session.get(SESSION_KEY) match {
        case Some(_) => Redirect("/")
          .flashing("info" -> "You are already logged in.")
        case None => Ok(views.html.register(registerForm, routes.AuthController.register()))
      }
  }

  def doregister = Action { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = ???
    val successFunction = ???
    val formValidationResult: Form[RegisterForm] = registerForm.bindFromRequest
    formValidationResult.fold(
      errorFunction,
      successFunction
    )
  }

  def logout = Action {
    Redirect("/").withNewSession
  }

}
