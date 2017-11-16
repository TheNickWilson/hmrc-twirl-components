package uk.gov.hmrc.twirl

import org.jsoup.Jsoup
import uk.gov.hmrc.twirl.html.radios
import uk.gov.hmrc.twirl.viewmodels.RadioOption

import scala.collection.JavaConverters._

class RadiosSpec extends SpecBase with MockFields {

  "radios" must {

    "render a default radio group" in {

      val radioButtons = Seq(
        RadioOption("id_1", "1", "some.label.1"),
        RadioOption("id_2", "2", "some.label.2")
      )

      val output: String = radios(
        field = normalField,
        legend = "some.message",
        radioButtons = radioButtons
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val groupElement = doc.select(".form-group")
      groupElement mustNot be(empty)
      groupElement mustNot haveClass("form-group-error")

      val fieldsetElement = doc.select("fieldset")
      fieldsetElement must haveAttr("id", "my_id")
      fieldsetElement mustNot haveAttr("class")

      val legendElement = doc.select("legend")
      legendElement.text mustEqual "some.message"

      legendElement.select("span:first-child") mustNot haveClass("visually-hidden")

      doc.select(".error-message") must be(empty)
      doc.select(".form-hint") must be(empty)

      val radioElements = doc.select(".multiple-choice")
      radioElements.size mustEqual 2

      radioElements.asScala.zip(radioButtons).foreach {
        case (dom, radio) =>

          val inputElement = dom.select("input")
          inputElement must haveAttr("type", "radio")
          inputElement must haveAttr("id", s"${normalField.id}-${radio.id}")
          inputElement must haveAttr("name", normalField.name)
          inputElement must haveAttr("value", radio.value)
          inputElement mustNot haveAttr("checked")
          inputElement mustNot haveAttr("data-target")

          val labelElement = dom.select("label")
          labelElement must haveAttr("for", s"${normalField.id}-${radio.id}")
          labelElement.text mustEqual radio.label
      }
    }

    "render a preset radio group" in {

      val radioButtons = Seq(
        RadioOption("id_1", "1", "some.label.1"),
        RadioOption("id_2", "2", "some.label.2")
      )

      val output: String = radios(
        field = fieldWithValue("1"),
        legend = "some.message",
        radioButtons = radioButtons
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      doc.select("input[value=1]") must haveAttr("checked")
      doc.select("input[value=2]") mustNot haveAttr("checked")
    }

    "render a radio group with errors" in {

      val radioButtons = Seq(
        RadioOption("id_1", "1", "some.label.1"),
        RadioOption("id_2", "2", "some.label.2")
      )

      val output: String = radios(
        field = fieldWithError,
        legend = "some.message",
        radioButtons = radioButtons
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val groupElement = doc.select(".form-group")
      groupElement must haveClass("form-group-error")

      val errorElement = doc.select(".error-message")
      errorElement.text mustEqual "some error"
    }

    "render a radio group with extra legend classes" in {

      val radioButtons = Seq(
        RadioOption("id_1", "1", "some.label.1"),
        RadioOption("id_2", "2", "some.label.2")
      )

      val output: String = radios(
        field = normalField,
        legend = "some.message",
        radioButtons = radioButtons,
        legendClasses = Set("some-extra-class")
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val legendElement = doc.select("legend > span:first-child")
      legendElement must haveClass("some-extra-class")
    }

    "render a radio group with a data-target attribute" in {

      val radioButtons = Seq(
        RadioOption("id_1", "1", "some.label.1", dataTarget = Some("some-data-target")),
        RadioOption("id_2", "2", "some.label.2")
      )

      val output: String = radios(
        field = normalField,
        legend = "some.message",
        radioButtons = radioButtons
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      doc.select("input[value=1]").parents() must haveAttr("data-target", "some-data-target")
      doc.select("input[value=2]").parents() mustNot haveAttr("data-target")
    }

    "render a radio group with a hidden legend" in {

      val radioButtons = Seq(
        RadioOption("id_1", "1", "some.label.1"),
        RadioOption("id_2", "2", "some.label.2")
      )

      val output: String = radios(
        field = normalField,
        legend = "some.message",
        hiddenLegend = true,
        radioButtons = radioButtons
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val legendElement = doc.select("legend > span:first-child")
      legendElement must haveClass("visually-hidden")
    }

    "render an inline radio group" in {

      val radioButtons = Seq(
        RadioOption("id_1", "1", "some.label.1"),
        RadioOption("id_2", "2", "some.label.2")
      )

      val output: String = radios(
        field = normalField,
        legend = "some.message",
        inline = true,
        radioButtons = radioButtons
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val fieldsetElement = doc.select("fieldset")
      fieldsetElement must haveClass("inline")
    }
  }
}
