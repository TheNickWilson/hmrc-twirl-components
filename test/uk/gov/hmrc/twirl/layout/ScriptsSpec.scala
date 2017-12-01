package uk.gov.hmrc.twirl.layout

import org.jsoup.Jsoup
import play.twirl.api.Html
import uk.gov.hmrc.twirl.SpecBase
import uk.gov.hmrc.twirl.layout.html.scripts

class ScriptsSpec extends SpecBase {

  "scripts" must {

    "render correctly" in {

      val config = configuration(
        "google-analytics.token" -> "foobar",
        "google-analytics.host" -> "baz",
        "assets.url" -> "assets/",
        "assets.version" -> "3.0.0"
      )

      val result: String = scripts(config).toString
      val doc = Jsoup.parseBodyFragment(result)

      doc.select("script[src=assets/3.0.0/javascripts/application.min.js]") mustNot be(empty)

      val gaElement = doc.select("script").first.toString
      gaElement must include("ga('create', 'foobar', 'baz');")
      gaElement must include("ga('send', 'pageview', { anonymizeIp: true });")
    }

    "render with extra gaScripts" in {

      val config = configuration(
        "google-analytics.token" -> "foobar",
        "google-analytics.host" -> "baz",
        "assets.url" -> "assets/",
        "assets.version" -> "3.0.0"
      )

      val result: String = scripts(config, Html("someExtraScripts")).toString
      val doc = Jsoup.parseBodyFragment(result)

      val gaElement = doc.select("script").first.toString
      gaElement must include("ga('create', 'foobar', 'baz');")
      gaElement mustNot include("ga('send', 'pageview', { anonymizeIp: true });")
      gaElement must include("someExtraScripts")
    }

    "render without google analytics" in {

      val config = configuration(
        "assets.url" -> "assets/",
        "assets.version" -> "3.0.0"
      )

      val result: String = scripts(config).toString
      val doc = Jsoup.parseBodyFragment(result)

      doc.select("script[src=assets/3.0.0/javascripts/application.min.js]") mustNot be(empty)

      val gaElement = doc.select("script").first.toString
      gaElement mustNot include("ga('create', 'foobar', 'baz');")
      gaElement mustNot include("ga('send', 'pageview', { anonymizeIp: true });")
    }

    "fail to render when extra scripts are passed but there is no gaToken" in {

      val config = configuration(
        "assets.url" -> "assets/",
        "assets.version" -> "3.0.0"
      )

      val exception = intercept[IllegalArgumentException] {
        scripts(config, Html("someExtraScripts"))
      }

      exception.getMessage mustEqual "requirement failed: No analytics token found therefore custom GA scripts won't run."
    }
  }
}
