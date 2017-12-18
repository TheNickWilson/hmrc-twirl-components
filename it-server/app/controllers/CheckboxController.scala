package controllers

import com.google.inject.Inject
import play.api.data.{Form, Mapping}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, Controller}
import uk.gov.hmrc.viewmodels.layout.LayoutConfiguration
import views.html.checkbox_page

class CheckboxController @Inject() (
                                     val messagesApi: MessagesApi,
                                     config: LayoutConfiguration
                                   ) extends Controller with I18nSupport {

  sealed trait Select

  object Select {

    case object First extends Select
    case object Second extends Select
    case object Third extends Select

    val mappings: Map[String, Select] = Map(
      "First"  -> First,
      "Second" -> Second,
      "Third"  -> Third
    )
  }

  private val selectMapping: Mapping[Set[Select]] = {
    import play.api.data.Forms._
    set(nonEmptyText)
      .verifying("error.invalid", _.forall(Select.mappings.keySet.contains _))
      .transform(_.map(Select.mappings.apply), _.map(_.toString))
  }

  private val form: Form[Set[Select]] = {
    Form(
      "input" -> selectMapping
        .verifying("error.required", _.nonEmpty)
    )
  }

  def get() = Action {
    implicit request =>
      Ok(checkbox_page(form, config))
  }

  def post() = Action {
    implicit request =>
      form.bindFromRequest().fold(
        errorForm =>
          BadRequest(checkbox_page(errorForm, config)),
        selects =>
          Ok(checkbox_page(form.fill(selects), config))
      )
  }
}
