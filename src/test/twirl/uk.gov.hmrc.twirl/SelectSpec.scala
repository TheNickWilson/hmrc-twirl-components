package uk.gov.hmrc.twirl

import org.jsoup.Jsoup
import uk.gov.hmrc.twirl.html.select

class SelectSpec extends SpecBase with MockFields {

  "select.scala.html" must {

    "render a default select with a single input" in {

      val output: String = select(
        field = normalField,
        label = "some.message",
        options = Seq("some value" -> "some text")
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val groupElement = doc.select(".form-group")
      groupElement mustNot haveClass("form-group--error")

      val hintElement = doc.select(".form-hint")
      hintElement.isEmpty mustEqual true

      val selectElement = doc.select("select")
      selectElement must haveAttr("id", "my_id")
      selectElement must haveAttr("name", "my.name")

      val optionElement = doc.select("option")
      optionElement.size mustEqual 1
      optionElement must haveAttr("value", "some value")
      optionElement.text mustEqual "some text"
    }

    "render a select with a placeholder option" in {

      val output: String = select(
        field = normalField,
        label = "some.message",
        placeholder = Some("some placeholder"),
        options = Seq.empty
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val optionElement = doc.select("option")
      optionElement.size mustEqual 1
      optionElement must haveAttr("value", "")
      optionElement.text mustEqual "some placeholder"
    }

    "render a select with an option pre-selected" in {

      val output: String = select(
        field = fieldWithValue("value 1"),
        label = "some.message",
        options = Seq(
          ("value 1", "some text 1"),
          ("value 2", "some text 2")
        )
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val optionElements = doc.select("option")
      optionElements.size mustEqual 2

      val firstOptionElement = doc.select("select option:first-child")
      firstOptionElement must haveAttr("selected")
    }

    "render a select with hint text when content is provided" in {

      val output: String = select(
        field = normalField,
        hint = Some("some hint text"),
        label = "some.message",
        options = Seq.empty
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val hintElement = doc.select(".form-hint")
      hintElement.text mustEqual "some hint text"
    }

    "render a select with errors when the field has errors" in {

      val output: String = select(
        field = fieldWithError,
        label = "some.message",
        options = Seq.empty
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val groupElement = doc.select(".form-group")
      groupElement must haveClass("form-group-error")

      val errorElement = doc.select(".error-message")
      errorElement.text mustEqual "some error"

      val selectElement = doc.select("select")
      selectElement must haveClass("form-control-error")
    }

    "render a select with custom label classes" in {

      val output: String = select(
        field = normalField,
        label = "some.message",
        labelClasses = Set("some-extra-class"),
        options = Seq.empty
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val labelElement = doc.select("label span.some-extra-class")
      labelElement.text mustEqual "some.message"
      labelElement mustNot haveClass("form-label-bold")
    }

    "render a select with custom input classes" in {

      val output: String = select(
        field = normalField,
        label = "some.message",
        inputClasses = Set("some-extra-class"),
        options = Seq.empty
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val selectElement = doc.select("select")
      selectElement must haveClass("some-extra-class")
    }

    "render an input with custom group classes" in {

      val output: String = select(
        field = normalField,
        label = "some.message",
        groupClasses = Set("some-extra-class"),
        options = Seq.empty
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val groupElement = doc.select(".form-group")
      groupElement must haveClass("some-extra-class")
    }
  }
}
