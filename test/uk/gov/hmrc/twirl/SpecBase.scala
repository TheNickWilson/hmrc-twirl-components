package uk.gov.hmrc.twirl

import org.jsoup.select.Elements
import org.scalatest.matchers.{MatchResult, Matcher}
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{MustMatchers, WordSpec}
import play.api.i18n.{Lang, Messages}

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
}
