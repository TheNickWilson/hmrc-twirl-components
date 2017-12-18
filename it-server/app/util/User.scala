package util
import org.joda.time.DateTime

object User extends uk.gov.hmrc.viewmodels.layout.User {
  override def lastLoggedIn: DateTime = DateTime.now
}