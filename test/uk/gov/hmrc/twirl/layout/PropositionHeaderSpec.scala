package uk.gov.hmrc.twirl.layout

import org.joda.time.DateTime
import org.jsoup.Jsoup
import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito._
import org.mockito.ArgumentMatchers._
import play.api.mvc.Request
import play.api.test.FakeRequest
import uk.gov.hmrc.twirl.SpecBase
import uk.gov.hmrc.twirl.layout.html.proposition_header
import uk.gov.hmrc.viewmodels.layout.{LayoutConfiguration, User}

class PropositionHeaderSpec extends SpecBase with MockitoSugar {

  implicit val request: Request[_] = FakeRequest()

  "proposition_header" must {

    "render correctly when unauthenticated" in {

      val config = mock[LayoutConfiguration]
      when(config.indexUrl(any())) thenReturn "foobar"

      val result: String = proposition_header(config, None).toString
      val doc = Jsoup.parseBodyFragment(result)

      val linkElement = doc.select("#proposition-name")
      linkElement must haveAttr("href", "foobar")
      linkElement.text mustEqual "service.name"

      doc.select("#proposition-link") must be(empty)
    }

    "render signout link when authenticated" in {

      val config = mock[LayoutConfiguration]
      when(config.indexUrl(any())) thenReturn "foobar"
      when(config.signOutUrl(any())) thenReturn "/sign-out"

      val user: User = new User {
        override val lastLoggedIn: DateTime =
          new DateTime(2017, 1, 1, 9, 45)
      }

      val result: String = proposition_header(config, Some(user)).toString
      val doc = Jsoup.parseBodyFragment(result)

      val signoutElement = doc.select("#proposition-link a")
      signoutElement must haveAttr("href", "/sign-out")
      signoutElement.text mustEqual "template.sign.out"
    }
  }
}
