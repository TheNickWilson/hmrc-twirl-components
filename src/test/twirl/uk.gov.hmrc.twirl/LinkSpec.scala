package uk.gov.hmrc.twirl

import org.jsoup.Jsoup
import uk.gov.hmrc.twirl.html.link

class LinkSpec extends SpecBase {

  "link.scala.html" must {

    "render a default link" in {

      val output = link(
        text = "some text",
        url = "some/url"
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val linkElement = doc.select("a")
      linkElement must haveAttr("href", "some/url")
      linkElement.text mustEqual "some text"
      linkElement mustNot haveAttr("role")
    }

    "render a link with hidden text" in {
      val output = link(
        text = "some text",
        url = "some/url",
        hiddenText = Some("hidden text")
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val visibleTextElement = doc.select("a span:first-child")
      visibleTextElement must haveAttr("aria-hidden", "true")
      visibleTextElement.text mustEqual "some text"

      val hiddenTextElement = doc.select(".visually-hidden")
      hiddenTextElement.text mustEqual "hidden text"
    }

    "render a link with custom classes" in {
      val output = link(
        text = "some text",
        url = "some/url",
        linkClasses = Seq("some-class")
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val linkElement = doc.select("a")
      linkElement must haveAttr("class", "some-class")
    }

    "render a link as a button" in {
      val output = link(
        text = "some text",
        url = "some/url",
        isButton = true
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val linkElement = doc.select("a")
      linkElement must haveAttr("role", "button")
      linkElement must haveAttr("class", "button ")
    }

    "render a link as a button with custom classes" in {
      val output = link(
        text = "some text",
        url = "some/url",
        isButton = true,
        linkClasses = Seq("some-class")
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val linkElement = doc.select("a")
      linkElement must haveAttr("role", "button")
      linkElement must haveAttr("class", "button some-class")
    }
  }
}
