package uk.gov.hmrc.twirl

import org.jsoup.Jsoup
import uk.gov.hmrc.twirl.html.page_header

class PageHeaderSpec extends SpecBase {

  "page_header" must {

    "render a default heading" in {

      val output: String = page_header(
        heading = "some.heading"
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      doc.select("h1").text mustEqual "some.heading"
      doc.select(".heading-secondary") must be(empty)
    }

    "render a heading with a section" in {

      val output: String = page_header(
        heading = "some.heading",
        section = Some("some.section")
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      doc.select(".heading-secondary").text mustEqual "section.hidden some.section"
      doc.select(".visually-hidden").text mustEqual "section.hidden"
    }
  }
}
