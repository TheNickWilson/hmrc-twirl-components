package controllers

import javax.inject._

import play.api._
import play.api.data.Form
import play.api.data.FormError
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._
import uk.gov.hmrc.viewmodels.layout.LayoutConfiguration

@Singleton
class DateController @Inject() (val messagesApi: MessagesApi, config: LayoutConfiguration) extends Controller with I18nSupport {

  val form = {
    import play.api.data.Forms._
    Form(tuple(
      "dateOfBirth.day" -> number,
      "dateOfBirth.month" -> number,
      "dateOfBirth.year" -> number
    )).withError(FormError("dateOfBirth.day", "error"))
  }

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.date_page(form, config))
  }
}
