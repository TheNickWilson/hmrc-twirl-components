package uk.gov.hmrc.twirl

import org.jsoup.Jsoup
import play.api.data.FormError
import uk.gov.hmrc.twirl.html.error_summary

class ErrorSummarySpec extends SpecBase {

  "error_summary.scala.html" must {

    "not render any content when there are no errors" in {

      val output = error_summary(
        errors = Seq.empty
      ).toString.replaceAll("\\s", "")

      output mustBe empty
    }

    "render a default error summary when there is an error" in {

      val output = error_summary(
        errors = Seq(
          FormError("my.name", "some.error")
        )
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val errorSummaryElement = doc.select(".error-summary")
      errorSummaryElement must haveAttr("role", "alert")
      errorSummaryElement must haveAttr("aria-labelledby", "error-summary-heading")

      val headingElement = doc.select("#error-summary-heading")
      headingElement must haveAttr("class", "heading-medium error-summary-heading")
      headingElement.text mustEqual "error.summary.title"

      val errorListElement = doc.select(".error-summary-list")
      val errorItems = errorListElement.select("a")
      errorItems.size mustEqual 1

      val errorItem = errorListElement.select("li:first-child a")
      errorItem must haveAttr("href", "#my.name")
      errorItem.text mustBe "some.error"
    }

    "render when there are two errors" in {

      val output = error_summary(
        errors = Seq(
          FormError("my.name", "some.error"),
          FormError("my.other.name", "some.other.error")
        )
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val firstErrorElement = doc.select(".error-summary-list li:first-child a")
      firstErrorElement must haveAttr("href", "#my.name")
      firstErrorElement.text mustEqual "some.error"

      val secondErrorElement = doc.select(".error-summary-list li:nth-child(2) a")
      secondErrorElement must haveAttr("href", "#my.other.name")
      secondErrorElement.text mustEqual "some.other.error"
    }

    "render a specific heading when one is provided" in {

      val output = error_summary(
        errors = Seq(
          FormError("my.name", "some.error")
        ),
        heading = "some heading"
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val headingElement = doc.select("#error-summary-heading")
      headingElement.text mustEqual "some heading"
    }

    "render a summary when one is provided" in {

      val output = error_summary(
        errors = Seq(
          FormError("my.name", "some.error")
        ),
        summary = Some("some summary")
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      val summaryElement = doc.select("p")
      summaryElement.text mustEqual "some summary"
    }
  }
}
