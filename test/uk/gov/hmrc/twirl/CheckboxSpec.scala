package uk.gov.hmrc.twirl

import org.jsoup.Jsoup
import play.api.data.Form
import uk.gov.hmrc.twirl.html.checkbox

class CheckboxSpec extends SpecBase {

  val form: Form[Set[String]] = {

    import play.api.data.Forms._
    import play.api.data._

    Form("value" -> set(text))
  }

  "checkbox" must {

    "render a default checkbox" in {

      val output: String = checkbox(
        field = form("value"),
        label = "some.label",
        value = "foo"
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val containerElement = doc.select(".multiple-choice")
      containerElement mustNot haveAttr("data-target")

      val inputElement = doc.select("input")
      inputElement must haveAttr("id", "value-foo")
      inputElement must haveAttr("name", "value")

      val labelElement = doc.select("label")
      labelElement must haveAttr("for", "value-foo")
      labelElement.text mustEqual "some.label"
    }

    "render a checked checkbox" in {

      val field = form.fill(Set("foo"))("value")

      val output: String = checkbox(
        field = field,
        label = "some.label",
        value = "foo"
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      doc.select("input") must haveAttr("checked")
    }

    "render a checkbox with a data-target" in {

      val output: String = checkbox(
        field = form("value"),
        label = "some.label",
        value = "foo",
        dataTarget = Some("some-data-target")
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val containerElement = doc.select(".multiple-choice")
      containerElement must haveAttr("data-target", "some-data-target")
    }

    "render a checkbox with an index" in {

      val output: String = checkbox(
        field = form("value"),
        label = "some.label",
        value = "foo",
        index = Some(0)
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val inputElement = doc.select("input")
      inputElement must haveAttr("name", "value[0]")
    }
  }
}
