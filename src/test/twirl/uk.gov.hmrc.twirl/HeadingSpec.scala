package uk.gov.hmrc.twirl

import org.jsoup.Jsoup
import uk.gov.hmrc.twirl.html.heading

class HeadingSpec extends SpecBase {

  "heading" must {

    "render a default heading" in {

      val output: String = heading(
        heading = "some.heading"
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      doc.select("h1").text mustEqual "some.heading"
      doc.select(".heading-secondary") must be(empty)
    }

    "render a heading with a section" in {

      val output: String = heading(
        heading = "some.heading",
        section = Some("some.section")
      ).toString

      val doc = Jsoup.parseBodyFragment(output)

      doc.select(".heading-secondary").text mustEqual "section.hidden some.section"
    }
  }
}
