package uk.gov.hmrc.twirl

import org.jsoup.Jsoup
import play.twirl.api.Html
import uk.gov.hmrc.twirl.html.radios_nested

class RadiosNestedSpec extends SpecBase with MockFields {

  "radios_nested" must {

    "render a default radio fieldset" in {

      val output: String = radios_nested(
        field = normalField,
        legend = "some.message"
      )(Html("""<div id="test" />""")).toString

      val doc = Jsoup.parseBodyFragment(output)

      val fieldsetElement = doc.select("fieldset")
      fieldsetElement mustNot haveAttr("class")

      val legendElement = doc.select("legend > span:first-child")
      legendElement must haveClass("bold-small")
      legendElement.text mustEqual "some.message"

      doc.select(".error-message") mustBe empty
      doc.select(".form-hint") mustBe empty

      doc.select("#test") mustNot be(empty)
    }

    "render a radio fieldset with errors" in {

      val output: String = radios_nested(
        field = fieldWithError,
        legend = "some.message"
      )(Html("""<div id="test" />""")).toString

      val doc = Jsoup.parseBodyFragment(output)

      val errorElement = doc.select(".error-message")
      errorElement.text mustEqual "some error"
    }

    "render a radio fieldset with hint text" in {

      val output: String = radios_nested(
        field = normalField,
        legend = "some.message",
        hint = Some("some.hint.text")
      )(Html("""<div id="test" />""")).toString

      val doc = Jsoup.parseBodyFragment(output)

      val hintElement = doc.select(".form-hint")
      hintElement.text mustEqual "some.hint.text"
    }

    "render a radio fieldset with custom legend classes" in {

      val output: String = radios_nested(
        field = normalField,
        legend = "some.message",
        legendClasses = Set("some-extra-class")
      )(Html("""<div id="test" />""")).toString

      val doc = Jsoup.parseBodyFragment(output)

      val legendElement = doc.select("legend > span:first-child")
      legendElement must haveClass("some-extra-class")
    }

    "render a radio fieldset with custom fieldset classes" in {

      val output: String = radios_nested(
        field = normalField,
        legend = "some.message",
        fieldsetClasses = Set("some-extra-class")
      )(Html("""<div id="test" />""")).toString

      val doc = Jsoup.parseBodyFragment(output)

      val fieldsetElement = doc.select("fieldset")
      fieldsetElement must haveClass("some-extra-class")
    }

    "render an inline radio fieldset" in {

      val output: String = radios_nested(
        field = normalField,
        legend = "some.message",
        inline = true
      )(Html("""<div id="test" />""")).toString

      val doc = Jsoup.parseBodyFragment(output)

      val fieldsetElement = doc.select("fieldset")
      fieldsetElement must haveClass("inline")
    }

    "render a radio fieldset with a hidden legend" in {

      val output: String = radios_nested(
        field = normalField,
        legend = "some.message",
        hiddenLegend = true
      )(Html("""<div id="test" />""")).toString

      val doc = Jsoup.parseBodyFragment(output)

      val legendElement = doc.select("legend > span:first-child")
      legendElement must haveClass("visually-hidden")
    }
  }
}
