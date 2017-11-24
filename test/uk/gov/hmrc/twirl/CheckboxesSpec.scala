package uk.gov.hmrc.twirl

import org.jsoup.Jsoup
import play.api.data.{Form, FormError}
import uk.gov.hmrc.twirl.html.checkboxes
import uk.gov.hmrc.viewmodels._

import scala.collection.JavaConverters._

class CheckboxesSpec extends SpecBase {

  val form: Form[Set[String]] = {

    import play.api.data._
    import play.api.data.Forms._

    Form("value" -> set(text))
  }

  val options: Seq[InputOption] = Seq(
    InputOption("foo", "foo.message"),
    InputOption("bar", "bar.message")
  )

  "checkboxes" must {

    "render a checkbox group" in {

      val field = form("value")

      val output: String = checkboxes(
        field = field,
        legend = "some.legend",
        options = options
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val groupElement = doc.select(".form-group")
      groupElement mustNot be(empty)
      groupElement mustNot haveClass("form-group-error")
      groupElement must haveAttr("id", "value")

      val fieldsetElement = doc.select("fieldset")
      fieldsetElement mustNot haveAttr("class")

      val legendElement = doc.select("legend")
      legendElement.text mustEqual "some.legend"

      legendElement.select("span:first-child") mustNot haveClass("visually-hidden")

      doc.select(".error-message") must be(empty)
      doc.select(".form-hint") must be(empty)

      val checkboxElements = doc.select(".multiple-choice")
      checkboxElements.size mustEqual 2
      checkboxElements.asScala.zip(options).zipWithIndex.foreach {
        case ((dom, checkbox), index) =>

          val inputElement = dom.select("input")
          inputElement must haveAttr("type", "checkbox")
          inputElement must haveAttr("id", s"${field.id}-${checkbox.value}")
          inputElement must haveAttr("name", s"${field.name}[$index]")
          inputElement must haveAttr("value", checkbox.value)
          inputElement mustNot haveAttr("checked")
          inputElement mustNot haveAttr("data-target")

          val labelElement = dom.select("label")
          labelElement must haveAttr("for", s"${field.id}-${checkbox.value}")
          labelElement.text mustEqual checkbox.label
      }
    }

    "render a preset checkbox group" in {

      val field = form.fill(Set("bar"))("value")

      val output: String = checkboxes(
        field = field,
        legend = "some.legend",
        options = options
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      doc.select("input[value=foo]") mustNot haveAttr("checked")
      doc.select("input[value=bar]") must haveAttr("checked")
    }

    "render a checkbox group with errors" in {

      val field = form.withError(FormError("value", "some error"))("value")

      val output: String = checkboxes(
        field = field,
        legend = "some.message",
        options = options
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val groupElement = doc.select(".form-group")
      groupElement must haveClass("form-group-error")

      val errorElement = doc.select(".error-message")
      errorElement.text mustEqual "some error"
    }

    "render a checkbox group with extra legend classes" in {

      val field = form("value")

      val output: String = checkboxes(
        field = field,
        legend = "some.message",
        options = options,
        legendClasses = Set("some-extra-class")
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val legendElement = doc.select("legend > span:first-child")
      legendElement must haveClass("some-extra-class")
    }

    "render a checkbox group with a data-target attribute" in {

      val options: Seq[InputOption] = Seq(
        InputOption("foo", "foo.message", dataTarget = Some("some-data-target")),
        InputOption("foo", "bar.message")
      )

      val field = form("value")

      val output: String = checkboxes(
        field = field,
        legend = "some.message",
        options = options
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      doc.select("input[value=foo]").parents() must haveAttr("data-target", "some-data-target")
      doc.select("input[value=bar]").parents() mustNot haveAttr("data-target")
    }

    "render a checkbox group with a hidden legend" in {

      val field = form("value")

      val output: String = checkboxes(
        field = field,
        legend = "some.message",
        hiddenLegend = true,
        options = options
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val legendElement = doc.select("legend > span:first-child")
      legendElement must haveClass("visually-hidden")
    }
  }
}
