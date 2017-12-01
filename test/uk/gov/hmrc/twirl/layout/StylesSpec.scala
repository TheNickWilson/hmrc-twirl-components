package uk.gov.hmrc.twirl.layout

import org.jsoup.Jsoup
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import uk.gov.hmrc.twirl.SpecBase
import uk.gov.hmrc.twirl.layout.html.styles
import uk.gov.hmrc.viewmodels.layout.LayoutConfiguration

class StylesSpec extends SpecBase with MockitoSugar {

  "styles" must {

    "render correctly" in {

      val config = mock[LayoutConfiguration]
      when(config.assetsPrefix) thenReturn "assets"

      val result: String = styles(config).toString
      val doc = Jsoup.parseBodyFragment(result)

      doc.select("link[href=assets/stylesheets/application.min.css]") mustNot be(empty)

      result must include("assets/stylesheets/application-ie7.min.css")
      result must include("assets/stylesheets/application-ie.min.css")
    }
  }
}
