package uk.gov.hmrc.twirl

import org.jsoup.select.Elements
import org.scalatest.matchers.{MatchResult, Matcher}
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{MustMatchers, WordSpec}
import play.api.Configuration
import play.api.i18n.{Lang, Messages}
import play.api.mvc.Request
import uk.gov.hmrc.viewmodels.layout.LayoutConfiguration

class SpecBase extends WordSpec with MustMatchers with MockitoSugar {

  def messages(lang: Lang): Messages =
    Messages(lang, MockMessagesApi)

  implicit lazy val preferred: Messages = messages(Lang("en"))

  def haveAttr(attr: String): Matcher[Elements] = Matcher[Elements] {
    elements =>
      MatchResult(
        elements.hasAttr(attr),
        s"did not have attribute: $attr",
        s"had attribute: $attr"
      )
  }

  def haveAttr(attr: String, value: String): Matcher[Elements] = Matcher[Elements] {
    elements =>
      val attrValue = elements.attr(attr)
      MatchResult(
        attrValue == value,
        s"$attrValue was not: $value",
        s"value was: $attrValue"
      )
  }

  def haveClass(className: String): Matcher[Elements] = Matcher[Elements] {
    elements =>
      MatchResult(
        elements.attr("class").split("\\s+").contains(className),
        s"element did not contain class: $className",
        s"element contained class: $className"
      )
  }

  def configuration(entries: (String, String)*): LayoutConfiguration = {
    new LayoutConfiguration {

      override protected def config: Configuration =
        Configuration(entries: _*)

      override def feedbackUrl(implicit request: Request[_]) = ???
      override def indexUrl(implicit request: Request[_]) = ???
    }
  }
}
