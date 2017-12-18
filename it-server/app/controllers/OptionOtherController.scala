package controllers

import com.google.inject.Inject
import play.api.data.{Form, Mapping}
import play.api.data.validation.{Constraint, Invalid, Valid}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, Controller}
import uk.gov.hmrc.viewmodels.layout.LayoutConfiguration
import views.html.option_other_page

sealed trait Select

object Select {

  case object First extends Select
  case object Second extends Select
  case class Other(value: String) extends Select
}

class OptionOtherController @Inject() (
                                        val messagesApi: MessagesApi,
                                        config: LayoutConfiguration
                                      ) extends Controller with I18nSupport {

  import Select._

  val selectConstraint: Constraint[String] = Constraint {
    case "first" | "second" | "other" => Valid
    case _ => Invalid("error.invalid")
  }

  def toSelect(t: (String, Option[String])): Select =
    t match {
      case ("first", _) => First
      case ("second", _) => Second
      case ("other", Some(value)) => Other(value)
      case _ =>
        throw new Exception("...?")
    }

  def fromSelect(s: Select): (String, Option[String]) =
    s match {
      case First => ("first", None)
      case Second => ("second", None)
      case Other(value) => ("other", Some(value))
    }

  private val selectMapping: Mapping[Select] = {

    import play.api.data.Forms._
    import uk.gov.voa.play.form.ConditionalMappings._

    tuple(
      "type" -> nonEmptyText.verifying(selectConstraint),
      "other" -> mandatoryIfEqual("select.type", "other", nonEmptyText)
    ).transform(toSelect, fromSelect)
  }

  private val form: Form[Select] = {
    Form(
      "select" -> selectMapping
    )
  }

  def get() = Action {
    implicit request =>
      Ok(option_other_page(form, config))
  }

  def post() = Action {
    implicit request =>
      form.bindFromRequest().fold(
        errorForm =>
          Ok(option_other_page(errorForm, config)),
        select =>
          Ok(option_other_page(form.fill(select), config))
      )
  }
}
