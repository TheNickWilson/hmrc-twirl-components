package controllers

import com.google.inject.Inject
import play.api.data.Form
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, Controller}
import uk.gov.hmrc.viewmodels.layout.LayoutConfiguration
import views.html.input_page

class InputController @Inject() (
                                  val messagesApi: MessagesApi,
                                  config: LayoutConfiguration
                                ) extends Controller with I18nSupport {

  private val form: Form[String] = {
    import play.api.data.Forms._
    Form(
      "input" -> nonEmptyText(maxLength = 35)
    )
  }

  def get() = Action {
    implicit request =>
      Ok(input_page(form, config))
  }

  def post() = Action {
    implicit request =>
      form.bindFromRequest().fold(
        errorForm =>
          BadRequest(input_page(errorForm, config)),
        string =>
          Ok(input_page(form.fill(string), config))
      )
  }
}
