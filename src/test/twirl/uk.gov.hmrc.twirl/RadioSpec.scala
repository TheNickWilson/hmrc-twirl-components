package uk.gov.hmrc.twirl

import org.jsoup.Jsoup
import uk.gov.hmrc.twirl.html.radio

class RadioSpec extends SpecBase with MockFields {

  "radio" must {

    "render a default radio button" in {

      val output: String = radio(
        field = normalField,
        label = "some.message",
        value = "true"
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val containerElement = doc.select(".multiple-choice")
      containerElement mustNot haveAttr("data-target")

      val inputElement = doc.select("input")
      inputElement must haveAttr("type", "radio")
      inputElement must haveAttr("id", normalField.id)
      inputElement must haveAttr("name", normalField.name)
      inputElement must haveAttr("value", "true")
      inputElement mustNot haveAttr("checked")

      val labelElement = doc.select("label")
      labelElement must haveAttr("for", normalField.id)
      labelElement.text mustEqual "some.message"
    }

    "render a checked radio button" in {

      val output: String = radio(
        field = fieldWithValue("true"),
        label = "some.message",
        value = "true"
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val inputElement = doc.select("input")
      inputElement must haveAttr("checked")
    }

    "render a radio button with a data-target attribute" in {

      val output: String = radio(
        field = fieldWithValue("true"),
        label = "some.message",
        value = "true",
        dataTarget = Some("some-data-target")
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val containerElement = doc.select(".multiple-choice")
      containerElement must haveAttr("data-target", "some-data-target")
    }
  }
}
