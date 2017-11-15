package uk.gov.hmrc

import play.twirl.api.Html

import scala.language.implicitConversions

package object twirl {

  implicit def toHtml(string: String): Html =
    Html(string)
}
