package controllers

import javax.inject._

import play.api._
import play.api.data.Form
import play.api.data.Forms.boolean
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._
import uk.gov.hmrc.viewmodels.layout.LayoutConfiguration

@Singleton
class HomeController @Inject() (val messagesApi: MessagesApi, config: LayoutConfiguration) extends Controller with I18nSupport {

  val form: Form[Boolean] = {
    Form("value" -> boolean)
  }

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index(form, config))
  }
}
