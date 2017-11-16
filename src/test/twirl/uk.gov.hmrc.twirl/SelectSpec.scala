package uk.gov.hmrc.twirl

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.mockito.Mockito._
import play.api.data.{Field, FormError}
import uk.gov.hmrc.twirl.html.select

class SelectSpec extends SpecBase {

  val field: Field = mock[Field]

  "select.scala.html" must {

    "render a default select with a single input" in {

      when(field.id) thenReturn "my_id"
      when(field.name) thenReturn "my.name"
      when(field.value) thenReturn None
      when(field.hasErrors) thenReturn false
      when(field.errors) thenReturn Seq.empty

      val output: String = select(
        field = field,
        label = "some.message",
        options = Seq(("some value", "some text"))
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val groupElement = doc.select(".form-group")
      groupElement mustNot haveClass("form-group--error")

      val hintElement = doc.select(".form-hint")
      hintElement.isEmpty mustEqual true

      val selectElement = doc.select("select")
      selectElement must haveAttr("id", "my_id")
      selectElement must haveAttr("name", "my.name")
      selectElement must haveAttr("required")

      val optionElement = doc.select("option")
      optionElement.size mustEqual 1
      optionElement must haveAttr("value", "some value")
      optionElement.text mustEqual "some text"
    }

    "render a select with a placeholder option" in {

      when(field.id) thenReturn "my_id"
      when(field.name) thenReturn "my.name"
      when(field.value) thenReturn None
      when(field.hasErrors) thenReturn false
      when(field.errors) thenReturn Seq.empty

      val output: String = select(
        field = field,
        label = "some.message",
        placeholder = Some("some placeholder"),
        options = Seq()
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val optionElement = doc.select("option")
      optionElement.size mustEqual 1
      optionElement must haveAttr("value", "")
      optionElement.text mustEqual "some placeholder"
    }

    "render a select with an option pre-selected" in {

      when(field.id) thenReturn "my_id"
      when(field.name) thenReturn "my.name"
      when(field.value) thenReturn Some("value 1")
      when(field.hasErrors) thenReturn false
      when(field.errors) thenReturn Seq.empty

      val output: String = select(
        field = field,
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

      when(field.id) thenReturn "my_id"
      when(field.name) thenReturn "my.name"
      when(field.value) thenReturn None
      when(field.hasErrors) thenReturn false
      when(field.errors) thenReturn Seq.empty

      val output: String = select(
        field = field,
        hint = Some("some hint text"),
        label = "some.message",
        options = Seq.empty
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val hintElement = doc.select(".form-hint")
      hintElement.text mustEqual "some hint text"
    }

    "render a select with errors when the field has errors" in {

      when(field.id) thenReturn "my_id"
      when(field.name) thenReturn "my.name"
      when(field.value) thenReturn None
      when(field.hasErrors) thenReturn true
      when(field.errors) thenReturn Seq(
        FormError("my.name", "some error")
      )

      val output: String = select(
        field = field,
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

      when(field.id) thenReturn "my_id"
      when(field.name) thenReturn "my.name"
      when(field.value) thenReturn None
      when(field.hasErrors) thenReturn false
      when(field.errors) thenReturn Seq.empty

      val output: String = select(
        field = field,
        label = "some.message",
        labelClasses = Seq("some-extra-class"),
        options = Seq.empty
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val labelElement = doc.select("label span.some-extra-class")
      labelElement.text mustEqual "some.message"
      labelElement mustNot haveClass("form-label-bold")
    }

    "render a select with custom input classes" in {

      when(field.id) thenReturn "my_id"
      when(field.name) thenReturn "my.name"
      when(field.value) thenReturn None
      when(field.hasErrors) thenReturn false
      when(field.errors) thenReturn Seq.empty

      val output: String = select(
        field = field,
        label = "some.message",
        inputClasses = Seq("some-extra-class"),
        options = Seq.empty
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val selectElement = doc.select("select")
      selectElement must haveClass("some-extra-class")
    }

    "render an input with custom group classes" in {

      when(field.id) thenReturn "my_id"
      when(field.name) thenReturn "my.name"
      when(field.value) thenReturn None
      when(field.hasErrors) thenReturn false
      when(field.errors) thenReturn Seq.empty

      val output: String = select(
        field = field,
        label = "some.message",
        groupClasses = Seq("some-extra-class"),
        options = Seq.empty
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val groupElement = doc.select(".form-group")
      groupElement must haveClass("some-extra-class")
    }
  }
}
