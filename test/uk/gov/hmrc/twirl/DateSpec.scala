package uk.gov.hmrc.twirl

import org.joda.time.LocalDate
import org.jsoup.Jsoup
import play.api.data.validation.{Constraint, Invalid, Valid}
import play.api.data.{Form, Mapping}
import uk.gov.hmrc.twirl.html.date

import scala.util.control.Exception.nonFatalCatch

class DateSpec extends SpecBase {

  "date" must {

    "render correctly" in {

      val output: String = date(
        field = form("date"),
        legend = "Date"
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val groupElement = doc.select(".form-group")
      groupElement must haveAttr("id", "date")
      groupElement mustNot haveClass("form-group-error")

      val legendElement = doc.select("legend > span")
      legendElement must haveClass("bold-small")
      legendElement mustNot haveClass("visually-hidden")
      legendElement.text mustEqual "Date"

      doc.select(".form-hint") must be(empty)

      doc.select(".error-message") must be(empty)

      val dayField = doc.select("#date_day")
      dayField must haveAttr("type", "number")
      dayField must haveAttr("min", "1")
      dayField must haveAttr("max", "31")

      val dayGroup = doc.select("#date_day-form")
      dayGroup must haveClass("form-group")
      dayGroup must haveClass("form-group-day")
      dayGroup.select("label").text mustEqual "field.day"

      val monthField = doc.select("#date_month")
      monthField must haveAttr("type", "number")
      monthField must haveAttr("min", "1")
      monthField must haveAttr("max", "12")

      val monthGroup = doc.select("#date_month-form")
      monthGroup must haveClass("form-group")
      monthGroup must haveClass("form-group-month")
      monthGroup.select("label").text mustEqual "field.month"

      val yearField = doc.select("#date_year")
      yearField must haveAttr("type", "number")

      val yearGroup = doc.select("#date_year-form")
      yearGroup must haveClass("form-group")
      yearGroup must haveClass("form-group-year")
      yearGroup.select("label").text mustEqual "field.year"
    }

    "render with an error on the date field" in {

      val output: String = date(
        field = form.withError("date", "error")("date"),
        legend = "Date"
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      doc.select(".form-group") must haveClass("form-group-error")
      doc.select(".error-message").text mustEqual "error"
    }

    "render with an error on the day field" in {

      val output: String = date(
        field = form.withError("date.day", "error")("date"),
        legend = "Date"
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      doc.select(".form-group") must haveClass("form-group-error")
      doc.select(".error-message").text mustEqual "error"
      doc.select("#date_day") must haveClass("form-control-error")
    }

    "render with an error on the month field" in {

      val output: String = date(
        field = form.withError("date.month", "error")("date"),
        legend = "Date"
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      doc.select(".form-group") must haveClass("form-group-error")
      doc.select(".error-message").text mustEqual "error"
      doc.select("#date_month") must haveClass("form-control-error")
    }

    "render with an error on the year field" in {

      val output: String = date(
        field = form.withError("date.year", "error")("date"),
        legend = "Date"
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      doc.select(".form-group") must haveClass("form-group-error")
      doc.select(".error-message").text mustEqual "error"
      doc.select("#date_year") must haveClass("form-control-error")
    }

    "render with hidden legend" in {

      val output: String = date(
        field = form("date"),
        legend = "Date",
        hiddenLegend = true
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      doc.select("legend > span") must haveClass("visually-hidden")
    }

    "render with hint text" in {

      val output: String = date(
        field = form("date"),
        legend = "Date",
        hint = Some("hint.text")
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      doc.select(".form-hint").text mustEqual "hint.text"
    }
  }

  private lazy val validDate: Constraint[(Int, Int, Int)] = Constraint {
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

  private lazy val localDateMapping: Mapping[LocalDate] = {

    import play.api.data.Forms._

    tuple(
      "day"   -> number,
      "month" -> number,
      "year"  -> number
    ).verifying(validDate).transform(toLocalDate, fromLocalDate)
  }

  private lazy val form: Form[LocalDate] = {

    Form(
      "date" -> localDateMapping
    )
  }
}
