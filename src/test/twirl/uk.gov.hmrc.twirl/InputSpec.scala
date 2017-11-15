package uk.gov.hmrc.twirl

import org.jsoup.Jsoup
import org.mockito.Mockito._
import play.api.data.{Field, FormError}
import uk.gov.hmrc.twirl.html.input

class InputSpec extends SpecBase {

  val field: Field = mock[Field]

  "input.scala.html" must {

    "render a default input" in {

      when(field.id) thenReturn "my_id"
      when(field.name) thenReturn "my.name"
      when(field.value) thenReturn None
      when(field.hasErrors) thenReturn false
      when(field.errors) thenReturn Seq.empty

      val output: String = input(
        field = field,
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
      inputElement must haveAttr("required")
      inputElement mustNot haveAttr("maxLength")

      val labelElement = doc.select("label")
      labelElement.text mustEqual "some.message"
      labelElement must haveAttr("for", "my_id")
    }

    "render a prefilled input" in {

      when(field.id) thenReturn "my_id"
      when(field.name) thenReturn "my.name"
      when(field.value) thenReturn Some("some text")
      when(field.hasErrors) thenReturn false
      when(field.errors) thenReturn Seq.empty

      val output: String = input(
        field = field,
        label = "some.message"
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val inputElement = doc.select("input")
      inputElement must haveAttr("value", "some text")
    }

    "render an input with hint text when content is provided" in {

      when(field.id) thenReturn "my_id"
      when(field.name) thenReturn "my.name"
      when(field.value) thenReturn None
      when(field.hasErrors) thenReturn false
      when(field.errors) thenReturn Seq.empty

      val output: String = input(
        field = field,
        hint = Some("some hint text"),
        label = "some.message"
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val hintElement = doc.select(".form-hint")
      hintElement.text mustEqual "some hint text"
    }

    "render an input with errors when the field has errors" in {

      when(field.id) thenReturn "my_id"
      when(field.name) thenReturn "my.name"
      when(field.value) thenReturn None
      when(field.hasErrors) thenReturn true
      when(field.errors) thenReturn Seq(
        FormError("my.name", "some error")
      )

      val output: String = input(
        field = field,
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

      when(field.id) thenReturn "my_id"
      when(field.name) thenReturn "my.name"
      when(field.value) thenReturn None
      when(field.hasErrors) thenReturn false
      when(field.errors) thenReturn Seq.empty

      val output: String = input(
        field = field,
        label = "some.message",
        labelClasses = Seq("some-extra-class")
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val labelElement = doc.select("label span.some-extra-class")
      labelElement.text mustEqual "some.message"
      labelElement mustNot haveClass("form-label-bold")
    }

    "render an input with custom input classes" in {

      when(field.id) thenReturn "my_id"
      when(field.name) thenReturn "my.name"
      when(field.value) thenReturn None
      when(field.hasErrors) thenReturn false
      when(field.errors) thenReturn Seq.empty

      val output: String = input(
        field = field,
        label = "some.message",
        inputClasses = Seq("some-extra-class")
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val inputElement = doc.select("input")
      inputElement must haveClass("some-extra-class")
    }

    "render an input with custom group classes" in {

      when(field.id) thenReturn "my_id"
      when(field.name) thenReturn "my.name"
      when(field.value) thenReturn None
      when(field.hasErrors) thenReturn false
      when(field.errors) thenReturn Seq.empty

      val output: String = input(
        field = field,
        label = "some.message",
        groupClasses = Seq("some-extra-class")
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val groupElement = doc.select(".form-group")
      groupElement must haveClass("some-extra-class")
    }

    "render an input with `maxLength`" in {

      when(field.id) thenReturn "my_id"
      when(field.name) thenReturn "my.name"
      when(field.value) thenReturn None
      when(field.hasErrors) thenReturn false
      when(field.errors) thenReturn Seq.empty

      val output: String = input(
        field = field,
        label = "some.message",
        maxLength = Some(10)
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val inputElement = doc.select("input")
      inputElement must haveAttr("maxLength", "10")
    }
  }
}
