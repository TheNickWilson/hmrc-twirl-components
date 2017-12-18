package uk.gov.hmrc.twirl

import org.jsoup.Jsoup
import play.api.data.Form
import play.twirl.api.Html
import uk.gov.hmrc.twirl.html.fieldset

class FieldsetSpec extends SpecBase with MockFields {

  "fieldset" must {

    "render a default fieldset" in {

      val output: String = fieldset(
        field = normalField,
        legend = "some.message"
      )(Html("""<div id="test" />""")).toString

      val doc = Jsoup.parseBodyFragment(output)

      val groupElement = doc.select(".form-group")
      groupElement must haveAttr("id", normalField.id)

      val fieldsetElement = doc.select("fieldset")
      fieldsetElement mustNot haveAttr("class")

      val legendElement = doc.select("legend > span:first-child")
      legendElement must haveClass("bold-small")
      legendElement.text mustEqual "some.message"

      doc.select(".error-message") mustBe empty
      doc.select(".form-hint") mustBe empty

      doc.select("#test") mustNot be(empty)
    }

    "render a fieldset with errors" in {

      val form = {
        import play.api.data.Forms._
        Form(
          "foo" -> text
        ).withError("foo", "some error")
      }

      val output: String = fieldset(
        field = form("foo"),
        legend = "some.message"
      )(Html("""<div id="test" />""")).toString

      val doc = Jsoup.parseBodyFragment(output)

      val fieldsetElement = doc.select("#foo")
      fieldsetElement must haveClass("form-group-error")

      val errorElement = doc.select(".error-message")
      errorElement.text mustEqual "some error"
    }

    "render a fieldset with hint text" in {

      val output: String = fieldset(
        field = normalField,
        legend = "some.message",
        hint = Some("some.hint.text")
      )(Html("""<div id="test" />""")).toString

      val doc = Jsoup.parseBodyFragment(output)

      val hintElement = doc.select(".form-hint")
      hintElement.text mustEqual "some.hint.text"
    }

    "render a fieldset with custom legend classes" in {

      val output: String = fieldset(
        field = normalField,
        legend = "some.message",
        legendClasses = Set("some-extra-class")
      )(Html("""<div id="test" />""")).toString

      val doc = Jsoup.parseBodyFragment(output)

      val legendElement = doc.select("legend > span:first-child")
      legendElement must haveClass("some-extra-class")
    }

    "render a fieldset with custom fieldset classes" in {

      val output: String = fieldset(
        field = normalField,
        legend = "some.message",
        fieldsetClasses = Set("some-extra-class")
      )(Html("""<div id="test" />""")).toString

      val doc = Jsoup.parseBodyFragment(output)

      val fieldsetElement = doc.select("fieldset")
      fieldsetElement must haveClass("some-extra-class")
    }

    "render an inline fieldset" in {

      val output: String = fieldset(
        field = normalField,
        legend = "some.message",
        inline = true
      )(Html("""<div id="test" />""")).toString

      val doc = Jsoup.parseBodyFragment(output)

      val fieldsetElement = doc.select("fieldset")
      fieldsetElement must haveClass("inline")
    }

    "render a fieldset with a hidden legend" in {

      val output: String = fieldset(
        field = normalField,
        legend = "some.message",
        hiddenLegend = true
      )(Html("""<div id="test" />""")).toString

      val doc = Jsoup.parseBodyFragment(output)

      val legendElement = doc.select("legend > span:first-child")
      legendElement must haveClass("visually-hidden")
    }

    "render a fieldset with an error on a sub field" in {

      val form = {
        import play.api.data.Forms._
        Form(
          "foo" -> single("bar" -> text)
        ).withError("foo.bar", "some error")
      }

      val output: String = fieldset(
        field = form("foo"),
        subFields = Seq(form("foo")("bar")),
        legend = "some.message"
      )(Html("""<div id="test" />""")).toString

      val doc = Jsoup.parseBodyFragment(output)

      val fieldsetElement = doc.select("#foo")
      fieldsetElement must haveClass("form-group-error")

      val errorElement = doc.select(".error-message")
      errorElement.text mustEqual "some error"
    }
  }
}
