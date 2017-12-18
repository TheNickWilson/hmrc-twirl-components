package controllers

import com.google.inject.Inject
import play.api.data.Form
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, Controller}
import uk.gov.hmrc.viewmodels.layout.LayoutConfiguration
import views.html.textarea_page

class TextareaController @Inject() (
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
      Ok(textarea_page(form, config))
  }

  def post() = Action {
    implicit request =>
      form.bindFromRequest().fold(
        errorForm =>
          BadRequest(textarea_page(errorForm, config)),
        value =>
          Ok(textarea_page(form.fill(value), config))
      )
  }
}
