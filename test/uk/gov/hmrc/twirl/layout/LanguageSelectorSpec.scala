package uk.gov.hmrc.twirl.layout

import org.jsoup.Jsoup
import play.api.Configuration
import play.api.i18n.Lang
import play.api.mvc.{AnyContent, Request}
import play.api.test.FakeRequest
import uk.gov.hmrc.twirl.SpecBase
import uk.gov.hmrc.twirl.layout.html.language_selector
import uk.gov.hmrc.viewmodels.layout.LayoutConfiguration

class LanguageSelectorSpec extends SpecBase {

  implicit val request: Request[AnyContent] = FakeRequest()

  object MockConfig extends LayoutConfiguration {
    override protected def config: Configuration = ???
    override def indexUrl(implicit request: Request[_]): String = ???
    override def feedbackUrl(implicit request: Request[_]): String = ???
    override def setLanguageUrl(lang: Lang)(implicit request: Request[_]): String =
      lang.language + "-url"
  }

  "language_selector" must {

    "render correctly when lang is `en`" in {

      val result: String = language_selector(MockConfig).toString
      val doc = Jsoup.parseBodyFragment(result)

      doc.select(".lang-select").text must include("English | Cymraeg")
      doc.select("span[aria-hidden=true]").text must include("|")

      val linkElement = doc.select(".lang-select a")
      linkElement.text mustEqual "Cymraeg"
      linkElement must haveAttr("href", "cy-url")
    }

    "render correctly when lang is `cy`" in {

      val result: String =
        language_selector(MockConfig)(request, messages(Lang("cy"))).toString
      val doc = Jsoup.parseBodyFragment(result)

      val linkElement = doc.select(".lang-select a")
      linkElement.text mustEqual "English"
      linkElement must haveAttr("href", "en-url")
    }
  }
}
