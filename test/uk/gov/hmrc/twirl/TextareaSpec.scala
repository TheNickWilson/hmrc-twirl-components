package uk.gov.hmrc.twirl

import org.jsoup.Jsoup
import uk.gov.hmrc.twirl.html.textarea

class TextareaSpec extends SpecBase with MockFields {

  "textarea.scala.html" must {

    "render a default textarea" in {

      val output: String = textarea(
        field = normalField,
        label = "some.messages"
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val groupElement = doc.select(".form-group")
      groupElement mustNot haveClass("form-group--error")

      val hintElement = doc.select(".form-hint")
      hintElement.isEmpty mustEqual true

      val textareaElement = doc.select("textarea")
      textareaElement must haveAttr("id", "my_id")
      textareaElement must haveAttr("name", "my.name")
      textareaElement.`val` mustEqual ""
      textareaElement mustNot haveAttr("maxLength")
      textareaElement must haveAttr("rows", "8")

      val labelElement = doc.select("label")
      labelElement.text mustEqual "some.messages"
      labelElement must haveAttr("for", "my_id")
    }

    "render a prefilled textarea" in {

      val output: String = textarea(
        field = fieldWithValue("some text"),
        label = "some.messages"
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val textareaElement = doc.select("textarea")
      textareaElement.`val` mustEqual "some text"
    }

    "render a textarea when hint text is provided" in {

      val output: String = textarea(
        field = normalField,
        hint = Some("some hint text"),
        label = "some.message"
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val hintElement = doc.select(".form-hint")
      hintElement.text mustEqual "some hint text"
    }

    "render a textarea with errors when the field has errors" in {

      val output: String = textarea(
        field = fieldWithError,
        label = "some.messages"
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val groupElement = doc.select(".form-group")
      groupElement must haveClass("form-group-error")

      val errorElement = doc.select(".error-message")
      errorElement.text mustEqual "some error"

      val inputElement = doc.select("textarea")
      inputElement must haveClass("form-control-error")
    }

    "render a textarea with custom label classes" in {

      val output: String = textarea(
        field = normalField,
        label = "some.message",
        labelClasses = Set("some-extra-class")
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val labelElement = doc.select("label span.some-extra-class")
      labelElement.text mustEqual "some.message"
      labelElement mustNot haveClass("form-label-bold")
    }

    "render a textarea with custom textarea classes" in {

      val output: String = textarea(
        field = normalField,
        label = "some.message",
        textareaClasses = Set("some-extra-class")
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val textareaElement = doc.select("textarea")
      textareaElement must haveClass("some-extra-class")
    }

    "render a textarea with custom group classes" in {

      val output: String = textarea(
        field = normalField,
        label = "some.message",
        groupClasses = Set("some-extra-class")
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val groupElement = doc.select(".form-group")
      groupElement must haveClass("some-extra-class")
    }

    "render a textarea with the right number of rows" in {

      val output: String = textarea(
        field = normalField,
        label = "some.message",
        rows = 10
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val textareaElement = doc.select("textarea")
      textareaElement must haveAttr("rows", "10")
    }

    "render a textarea with a hidden label" in {

      val output: String = textarea(
        field       = normalField,
        label       = "some.messages",
        hiddenLabel = true
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      doc.select("label > span") must haveClass("visually-hidden")
    }
  }
}
