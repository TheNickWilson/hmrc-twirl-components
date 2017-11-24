package uk.gov.hmrc

import play.twirl.api.Html

import scala.language.implicitConversions

package object twirl {

  implicit def toHtml(str: String): Html =
    Html(str)
}
