package uk.gov.hmrc.viewmodels.layout

import com.typesafe.config.ConfigException
import org.scalatest.{MustMatchers, OptionValues, WordSpec}
import play.api.Configuration
import uk.gov.hmrc.twirl.SpecBase

class LayoutConfigurationSpec extends SpecBase with OptionValues {

  ".assetsUrl" must {

    "return relevant configuration" in {
      val config: LayoutConfiguration = configuration(
        "assets.url" -> "foo"
      )
      config.assetsUrl mustEqual "foo"
    }

    "throw an exception when relevant configuration isn't present" in {
      val config: LayoutConfiguration = configuration()
      assertThrows[ConfigException.Missing] {
        config.assetsUrl
      }
    }
  }

  ".assetsVersion" must {

    "return relevant configuration" in {
      val config: LayoutConfiguration = configuration(
        "assets.version" -> "3.0.0"
      )
      config.assetsVersion mustEqual "3.0.0"
    }

    "throw an exception when relevant configuration isn't present" in {
      val config: LayoutConfiguration = configuration()
      assertThrows[ConfigException.Missing] {
        config.assetsVersion
      }
    }
  }

  ".assetsPrefix" must {

    "return relevant configuration" in {
      val config: LayoutConfiguration = configuration(
        "assets.url"     -> "foo",
        "assets.version" -> "bar"
      )
      config.assetsPrefix mustEqual "foobar"
    }

    "throw an exception when `assets.url` is missing" in {
      val config: LayoutConfiguration = configuration(
        "assets.version" -> "bar"
      )
      assertThrows[ConfigException.Missing] {
        config.assetsPrefix
      }
    }

    "throw an exception when `assets.version` is missing" in {
      val config: LayoutConfiguration = configuration(
        "assets.url" -> "foo"
      )
      assertThrows[ConfigException.Missing] {
        config.assetsPrefix
      }
    }
  }

  ".gaToken" must {

    "return the token when it is specified" in {
      val config: LayoutConfiguration = configuration(
        "google-analytics.token" -> "foobar"
      )
      config.gaToken.value mustEqual "foobar"
    }

    "return `None` when no token is specified" in {
      val config: LayoutConfiguration = configuration()
      config.gaToken must be(empty)
    }
  }

  ".gaHost" must {

    "return relevant configuration" in {
      val config: LayoutConfiguration = configuration(
        "google-analytics.host" -> "foobar"
      )
      config.gaHost mustEqual "foobar"
    }

    "throw an exception when the relevant configuration isn't present" in {
      val config: LayoutConfiguration = configuration()
      assertThrows[ConfigException.Missing] {
        config.gaHost
      }
    }
  }

  ".phase" must {

    "return relevant configuration for alpha" in {
      val config: LayoutConfiguration = configuration(
        "app.phase" -> "alpha"
      )
      config.phase.value mustEqual Phase.ALPHA
    }

    "return relevant configuration for beta" in {
      val config: LayoutConfiguration = configuration(
        "app.phase" -> "beta"
      )
      config.phase.value mustEqual Phase.BETA
    }

    "return `None` when no configuration is present" in {
      val config: LayoutConfiguration = configuration()
      config.phase must be(empty)
    }

    "throw an exception when the configuration is invalid" in {
      val config: LayoutConfiguration = configuration(
        "app.phase" -> "foobar"
      )
      assertThrows[NoSuchElementException] {
        config.phase
      }
    }
  }
}
