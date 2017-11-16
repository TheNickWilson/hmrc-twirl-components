package uk.gov.hmrc.twirl

import org.jsoup.Jsoup
import uk.gov.hmrc.twirl.html.back_link

class BackLinkSpec extends SpecBase {

  "back_link.scala.html" must {

    "render a back link" in {

      val output = back_link().toString

      val doc = Jsoup.parseBodyFragment(output)

      val linkElement = doc.select("a")
      linkElement must haveAttr("class", "link-back")
      linkElement.text mustEqual "link.back"
    }
  }
}
