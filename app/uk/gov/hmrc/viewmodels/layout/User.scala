package uk.gov.hmrc.viewmodels.layout

import org.joda.time.DateTime

trait User {

  def lastLoggedIn: DateTime
}
