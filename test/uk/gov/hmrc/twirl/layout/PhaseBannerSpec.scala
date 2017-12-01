package uk.gov.hmrc.twirl.layout

import org.jsoup.Jsoup
import play.api.Configuration
import play.api.mvc.Request
import play.api.test.FakeRequest
import uk.gov.hmrc.twirl.SpecBase
import uk.gov.hmrc.twirl.layout.html.phase_banner
import uk.gov.hmrc.viewmodels.layout.LayoutConfiguration

class PhaseBannerSpec extends SpecBase {

  implicit val request: Request[_] = FakeRequest()

  "phase_banner" must {

    "return nothing when no phase configuration is set" in {
      val config: LayoutConfiguration = configuration()
      phase_banner(config).toString.trim must be(empty)
    }

    "return an alpha banner when the phase is alpha" in {

      val config: LayoutConfiguration =
        configuration("app.phase" -> "alpha")

      val result: String = phase_banner(config).toString
      val doc = Jsoup.parseBodyFragment(result)

      doc.select(".phase-tag").text mustEqual "phase.alpha"
      doc.select(".phase-banner span").text mustEqual "phase.feedback args:"

      val linkElement = doc.select(".phase-banner a")
      linkElement must haveAttr("href", "foobar")
      linkElement must haveAttr("data-journey-click", "other-global:Click:Feedback")
    }
  }

  override def configuration(entries: (String, String)*): LayoutConfiguration = {
    new LayoutConfiguration {
      override protected def config: Configuration = Configuration(entries: _*)
      override def feedbackUrl(implicit request: Request[_]): String = "foobar"
      override def indexUrl(implicit request: Request[_]): String = ???
    }
  }
}
