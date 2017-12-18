package controllers

import javax.inject._

import org.joda.time.LocalDate
import play.api.data.{Form, Mapping}
import play.api.data.validation.{Constraint, Invalid, Valid}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._
import uk.gov.hmrc.viewmodels.layout.LayoutConfiguration

import scala.util.control.Exception.nonFatalCatch

class DateController @Inject() (val messagesApi: MessagesApi, config: LayoutConfiguration) extends Controller with I18nSupport {

  private val validDate: Constraint[(Int, Int, Int)] = Constraint {
    t =>
      nonFatalCatch.opt(toLocalDate(t))
        .map(_ => Valid)
        .getOrElse(Invalid("error.date.invalid"))
  }

  private def toLocalDate(t: (Int, Int, Int)): LocalDate =
    t match {
      case (day, month, year) =>
        new LocalDate(year, month, day)
    }

  private def fromLocalDate(d: LocalDate): (Int, Int, Int) =
    (d.getDayOfMonth, d.getMonthOfYear, d.getYear)

  private val localDateMapping: Mapping[LocalDate] = {

    import play.api.data.Forms._

    tuple(
      "day"   -> number,
      "month" -> number,
      "year"  -> number
    ).verifying(validDate).transform(toLocalDate, fromLocalDate)
  }

  private val form: Form[LocalDate] = {

    Form(
      "date" -> localDateMapping
    )
  }

  def get() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.date_page(form, config))
  }

  def post() = Action {
    implicit request =>
      form.bindFromRequest().fold(
        errorForm =>
          Ok(views.html.date_page(errorForm, config)),
        localDate =>
          Ok(views.html.date_page(form.fill(localDate), config))
      )
  }
}
