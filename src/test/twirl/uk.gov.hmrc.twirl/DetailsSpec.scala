package uk.gov.hmrc.twirl

import org.jsoup.Jsoup
import play.twirl.api.Html
import uk.gov.hmrc.twirl.html.details

class DetailsSpec extends SpecBase with MockFields {

  "details" must {

    "render a details/summary" in {

      val output: String = details("some.prompt")(Html("""<div id="test" />""")).toString
      val doc = Jsoup.parseBodyFragment(output)

      val summaryElement = doc.select("details > summary")
      summaryElement must haveClass("summary")
      summaryElement.text mustEqual "some.prompt"

      val innerElement = doc.select("details #test")
      innerElement mustNot be(empty)
    }
  }
}
