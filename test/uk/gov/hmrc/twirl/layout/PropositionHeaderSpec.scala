package uk.gov.hmrc.twirl.layout

import org.jsoup.Jsoup
import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito._
import org.mockito.ArgumentMatchers._
import play.api.mvc.Request
import play.api.test.FakeRequest
import uk.gov.hmrc.twirl.SpecBase
import uk.gov.hmrc.twirl.layout.html.proposition_header
import uk.gov.hmrc.viewmodels.layout.LayoutConfiguration

class PropositionHeaderSpec extends SpecBase with MockitoSugar {

  implicit val request: Request[_] = FakeRequest()

  "proposition_header" must {

    "render correctly" in {

      val config = mock[LayoutConfiguration]
      when(config.indexUrl(any())) thenReturn "foobar"

      val result: String = proposition_header(config).toString
      val doc = Jsoup.parseBodyFragment(result)

      val linkElement = doc.select(".header-proposition #proposition-menu a")

      linkElement must haveAttr("href", "foobar")
      linkElement.text mustEqual "service.name"
    }
  }
}
