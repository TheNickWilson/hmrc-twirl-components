package uk.gov.hmrc.viewmodels

import org.scalatest.{MustMatchers, WordSpec}
import play.api.data.Form

class RichFieldSpec extends WordSpec with MustMatchers {

  val multiForm: Form[Set[String]] = {

    import play.api.data.Forms._
    import play.api.data._

    Form("value" -> set(text))
  }

  val singleForm: Form[String] = {

    import play.api.data.Forms._
    import play.api.data._

    Form("value" -> text)
  }

  ".values" must {

    "return an empty set when a multi-value field has no value" in {
      val field = multiForm("value")
      field.values must be(empty)
    }

    "return an empty set when a single-value field has no value" in {
      val field = singleForm("value")
      field.values must be(empty)
    }

    "return multiple values when the field is a multi-value field" in {
      val field = multiForm.fill(Set("foo", "bar"))("value")
      field.values mustEqual Seq("foo", "bar")
    }

    "return a single value when the field a single-value field" in {
      val field = singleForm.fill("foo")("value")
      field.values mustEqual Seq("foo")
    }
  }
}
