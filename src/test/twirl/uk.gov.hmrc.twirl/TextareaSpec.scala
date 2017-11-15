package uk.gov.hmrc.twirl

import org.jsoup.Jsoup
import org.mockito.Mockito._
import play.api.data.{Field, FormError}
import uk.gov.hmrc.twirl.html.textarea

class TextareaSpec extends SpecBase {

  val field: Field = mock[Field]

  "textarea.scala.html" must {

    "render a default textarea" in {

      when(field.id) thenReturn "my_id"
      when(field.name) thenReturn "my.name"
      when(field.value) thenReturn None
      when(field.hasErrors) thenReturn false
      when(field.errors) thenReturn Seq.empty

      val output: String = textarea(
        field = field,
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
      textareaElement must haveAttr("required")
      textareaElement.`val` mustEqual ""
      textareaElement mustNot haveAttr("maxLength")
      textareaElement must haveAttr("rows", "8")

      val labelElement = doc.select("label")
      labelElement.text mustEqual "some.messages"
      labelElement must haveAttr("for", "my_id")
    }

    "render a prefilled textarea" in {

      when(field.id) thenReturn "my_id"
      when(field.name) thenReturn "my.name"
      when(field.value) thenReturn Some("some text")
      when(field.hasErrors) thenReturn false
      when(field.errors) thenReturn Seq.empty

      val output: String = textarea(
        field = field,
        label = "some.messages"
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val textareaElement = doc.select("textarea")
      textareaElement.`val` mustEqual "some text"
    }

    "render a textarea when hint text is provided" in {

      when(field.id) thenReturn "my_id"
      when(field.name) thenReturn "my.name"
      when(field.value) thenReturn None
      when(field.hasErrors) thenReturn false
      when(field.errors) thenReturn Seq.empty

      val output: String = textarea(
        field = field,
        hint = Some("some hint text"),
        label = "some.message"
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val hintElement = doc.select(".form-hint")
      hintElement.text mustEqual "some hint text"
    }

    "render a textarea with errors when the field has errors" in {

      when(field.id) thenReturn "my_id"
      when(field.name) thenReturn "my.name"
      when(field.value) thenReturn None
      when(field.hasErrors) thenReturn true
      when(field.errors) thenReturn Seq(
        FormError("my.name", "some error")
      )

      val output: String = textarea(
        field = field,
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

      when(field.id) thenReturn "my_id"
      when(field.name) thenReturn "my.name"
      when(field.value) thenReturn None
      when(field.hasErrors) thenReturn false
      when(field.errors) thenReturn Seq.empty

      val output: String = textarea(
        field = field,
        label = "some.message",
        labelClasses = Seq("some-extra-class")
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val labelElement = doc.select("label span.some-extra-class")
      labelElement.text mustEqual "some.message"
      labelElement mustNot haveClass("form-label-bold")
    }

    "render a textarea with custom textarea classes" in {

      when(field.id) thenReturn "my_id"
      when(field.name) thenReturn "my.name"
      when(field.value) thenReturn None
      when(field.hasErrors) thenReturn false
      when(field.errors) thenReturn Seq.empty

      val output: String = textarea(
        field = field,
        label = "some.message",
        textareaClasses = Seq("some-extra-class")
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val textareaElement = doc.select("textarea")
      textareaElement must haveClass("some-extra-class")
    }

    "render a textarea with custom group classes" in {

      when(field.id) thenReturn "my_id"
      when(field.name) thenReturn "my.name"
      when(field.value) thenReturn None
      when(field.hasErrors) thenReturn false
      when(field.errors) thenReturn Seq.empty

      val output: String = textarea(
        field = field,
        label = "some.message",
        groupClasses = Seq("some-extra-class")
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val groupElement = doc.select(".form-group")
      groupElement must haveClass("some-extra-class")
    }

    "render an optional textarea" in {

      when(field.id) thenReturn "my_id"
      when(field.name) thenReturn "my.name"
      when(field.value) thenReturn None
      when(field.hasErrors) thenReturn false
      when(field.errors) thenReturn Seq.empty

      val output: String = textarea(
        field = field,
        label = "some.message",
        required = false
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val textareaElement = doc.select("textarea")
      textareaElement mustNot haveAttr("required")
    }

    "render a textarea with `maxLength`" in {

      when(field.id) thenReturn "my_id"
      when(field.name) thenReturn "my.name"
      when(field.value) thenReturn None
      when(field.hasErrors) thenReturn false
      when(field.errors) thenReturn Seq.empty

      val output: String = textarea(
        field = field,
        label = "some.message",
        maxLength = Some(10)
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val textareaElement = doc.select("textarea")
      textareaElement must haveAttr("maxLength", "10")
    }

    "render a textarea with the right number of rows" in {

      when(field.id) thenReturn "my_id"
      when(field.name) thenReturn "my.name"
      when(field.value) thenReturn None
      when(field.hasErrors) thenReturn false
      when(field.errors) thenReturn Seq.empty

      val output: String = textarea(
        field = field,
        label = "some.message",
        rows = 10
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val textareaElement = doc.select("textarea")
      textareaElement must haveAttr("rows", "10")
    }
  }
}
