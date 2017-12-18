package uk.gov.hmrc.twirl

import org.jsoup.Jsoup
import uk.gov.hmrc.twirl.html.input

class InputSpec extends SpecBase with MockFields {

  "input.scala.html" must {

    "render a default input" in {

      val output: String = input(
        field = normalField,
        label = "some.message"
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val groupElement = doc.select(".form-group")
      groupElement mustNot haveClass("form-group--error")

      val hintElement = doc.select(".form-hint")
      hintElement.isEmpty mustEqual true

      val inputElement = doc.select("input")
      inputElement must haveAttr("id", "my_id")
      inputElement must haveAttr("name", "my.name")
      inputElement must haveAttr("value", "")
      inputElement must haveAttr("type", "text")
      inputElement mustNot haveAttr("maxLength")

      val labelElement = doc.select("label")
      labelElement.text mustEqual "some.message"
      labelElement must haveAttr("for", "my_id")
      labelElement.select("span") mustNot haveClass("visually-hidden")
    }

    "render a prefilled input" in {

      val output: String = input(
        field = fieldWithValue("some text"),
        label = "some.message"
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val inputElement = doc.select("input")
      inputElement must haveAttr("value", "some text")
    }

    "render an input with hint text when content is provided" in {

      val output: String = input(
        field = normalField,
        hint = Some("some hint text"),
        label = "some.message"
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val hintElement = doc.select(".form-hint")
      hintElement.text mustEqual "some hint text"
    }

    "render an input with errors when the field has errors" in {

      val output: String = input(
        field = fieldWithError,
        label = "some.message"
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val groupElement = doc.select(".form-group")
      groupElement must haveClass("form-group-error")

      val errorElement = doc.select(".error-message")
      errorElement.text mustEqual "some error"

      val inputElement = doc.select("input")
      inputElement must haveClass("form-control-error")
    }

    "render an input with custom label classes" in {

      val output: String = input(
        field = normalField,
        label = "some.message",
        labelClasses = Set("some-extra-class")
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val labelElement = doc.select("label span.some-extra-class")
      labelElement.text mustEqual "some.message"
      labelElement mustNot haveClass("form-label-bold")
    }

    "render an input with custom input classes" in {

      val output: String = input(
        field = normalField,
        label = "some.message",
        inputClasses = Set("some-extra-class")
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val inputElement = doc.select("input")
      inputElement must haveClass("some-extra-class")
    }

    "render an input with custom group classes" in {

      val output: String = input(
        field = normalField,
        label = "some.message",
        groupClasses = Set("some-extra-class")
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val groupElement = doc.select(".form-group")
      groupElement must haveClass("some-extra-class")
    }

    "render an input with extra attributes" in {

      val output: String = input(
        field      = normalField,
        label      = "some.message",
        attributes = Map(
          "maxLength" -> "10",
          "minLength" -> "1"
        )
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val inputElement = doc.select("input")
      inputElement must haveAttr("maxLength", "10")
      inputElement must haveAttr("minLength", "1")
    }

    "render an input with a different `type`" in {

      val output: String = input(
        field      = normalField,
        label      = "some.message",
        typeAttr    = "number"
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val inputElement = doc.select("input")
      inputElement must haveAttr("type", "number")
    }

    "render an inline input with errors" in {

      val output: String = input(
        field  = fieldWithError,
        label  = "some.message",
        inline = true
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val groupElement = doc.select(".form-group")
      groupElement mustNot haveClass("form-group-error")

      val errorElement = doc.select(".error-message")
      errorElement.text must be(empty)

      val inputElement = doc.select("input")
      inputElement must haveClass("form-control-error")
    }

    "render an input with a hidden label" in {

      val output: String = input(
        field = normalField,
        label = "some.message",
        hiddenLabel = true
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      doc.select("label > span") must haveClass("visually-hidden")
    }
  }
}
