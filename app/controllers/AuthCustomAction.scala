package controllers

import javax.inject.Inject
import play.api.Logging
import play.api.mvc._
import play.api.mvc.Results._

import scala.concurrent.{ExecutionContext, Future}

class AuthCustomAction @Inject() (parser: BodyParsers.Default)(implicit ec: ExecutionContext)
    extends ActionBuilderImpl(parser) {

  override def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {
    val username = request.session.get("username_session")
    username match {
      case None    => Future.successful(Redirect(routes.AuthController.login()))
      case Some(_) => block(request)
    }
  }

}
