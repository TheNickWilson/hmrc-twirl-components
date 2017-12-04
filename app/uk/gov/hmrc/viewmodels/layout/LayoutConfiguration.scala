package uk.gov.hmrc.viewmodels.layout

import play.api.Configuration
import play.api.mvc.Request

trait LayoutConfiguration {

  protected def config: Configuration

  def assetsUrl: String     = config.underlying.getString("assets.url")
  def assetsVersion: String = config.underlying.getString("assets.version")
  def assetsPrefix: String  = assetsUrl + assetsVersion

  def gaToken: Option[String] = config.getString("google-analytics.token")
  def gaHost: String          = config.underlying.getString("google-analytics.host")

  def phase: Option[Phase.Value] = config.getString("app.phase").map(Phase.withName)

  def indexUrl(implicit request: Request[_]): String
  def feedbackUrl(implicit request: Request[_]): String
}
