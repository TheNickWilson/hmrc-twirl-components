package config

import com.google.inject.Inject
import play.api.Configuration
import play.api.i18n.Lang
import play.api.mvc.Request
import uk.gov.hmrc.viewmodels.layout.LayoutConfiguration

class LayoutConfigurationImpl @Inject() (
                                          override protected val config: Configuration
                                        ) extends LayoutConfiguration {

  override def feedbackUrl(implicit request: Request[_]): String = "#"

  override def indexUrl(implicit request: Request[_]): String =
    controllers.routes.HomeController.index().absoluteURL()

  override def setLanguageUrl(lang: Lang)(implicit request: Request[_]): String =
    controllers.routes.LanguageController.setLanguage(lang).url

  override def signOutUrl(implicit request: Request[_]): String =
    controllers.routes.HomeController.index().absoluteURL()
}
