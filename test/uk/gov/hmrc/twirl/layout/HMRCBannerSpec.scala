package uk.gov.hmrc.twirl.layout

import org.joda.time.DateTime
import org.jsoup.Jsoup
import uk.gov.hmrc.twirl.SpecBase
import uk.gov.hmrc.twirl.layout.html.hmrc_banner
import uk.gov.hmrc.viewmodels.layout.User

class HMRCBannerSpec extends SpecBase {

  "hmrc_banner" must {

    "include last signed in when there is a user" in {

      val user = new User {
        override val lastLoggedIn: DateTime =
          new DateTime(2017, 1, 1, 9, 45)
      }

      val result: String = hmrc_banner(Some(user)).toString
      val doc = Jsoup.parseBodyFragment(result)

      doc.select(".logged-in-status").text mustEqual "template.last.signed.in args: 9:45AM, Sunday 1 January 2017"
    }

    "show no last signed in when there is no user" in {

      val result: String = hmrc_banner(None).toString
      val doc = Jsoup.parseBodyFragment(result)

      doc.select(".logged-in-status") must be(empty)
    }
  }
}
