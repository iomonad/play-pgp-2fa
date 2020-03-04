package object models {

  /**
    * Form Injection model
    */
  case class LoginForm(username: String, password: String)

  /**
    * Form injection model
    */
  case class RegisterForm(username: String, password: String, password2: String, pubkey: String)

}
