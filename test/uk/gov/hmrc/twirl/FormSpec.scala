package uk.gov.hmrc.twirl

import org.jsoup.Jsoup
import play.api.mvc.Call
import play.api.test._
import play.twirl.api.Html
import org.scalatestplus.play.guice._
import play.api.Application
import play.filters.csrf.{CSRFConfigProvider, CSRFFilter}
import play.filters.csrf.CSRF.Token
import uk.gov.hmrc.twirl.html.form

class FormSpec extends SpecBase with GuiceOneAppPerSuite {

  def addToken[T](fakeRequest: FakeRequest[T])(implicit app: Application): FakeRequest[_] = {
    val csrfConfig     = app.injector.instanceOf[CSRFConfigProvider].get
    val csrfFilter     = app.injector.instanceOf[CSRFFilter]
    val token          = csrfFilter.tokenProvider.generateToken

    fakeRequest.copyFakeRequest(tags = fakeRequest.tags ++ Map(
      Token.NameRequestTag  -> csrfConfig.tokenName,
      Token.RequestTag      -> token
    )).withHeaders((csrfConfig.headerName, token))
  }

  "form.scala.html" must {

    "render a form with a CSRF token" in {

      implicit val fr = addToken(FakeRequest())

      val call = Call("some method", "some/url")

      val output: String = form(call)(Html("""<div id="test" />""")).toString

      val doc = Jsoup.parseBodyFragment(output)

      val formElement = doc.select("form")
      formElement must haveAttr("method", "some method")
      formElement must haveAttr("action", "some/url")
      formElement must haveAttr("autocomplete", "off")
      formElement must haveAttr("novalidate")

      val innerElement = doc.select("form #test")
      innerElement mustNot be(empty)

      val csrfElement = doc.select("""[name="csrfToken"]""")
      csrfElement must haveAttr("value", fr.headers("Csrf-Token"))
    }
  }
}
