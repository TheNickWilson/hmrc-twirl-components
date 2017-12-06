package controllers

import com.google.inject.Inject
import play.api.http.HttpErrorHandler
import play.api.i18n.{I18nSupport, Lang, Langs, MessagesApi}
import play.api.mvc.{Action, AnyContent, Controller}

import scala.concurrent.Future

class LanguageController @Inject() (
                                     langs: Langs,
                                     errorHandler: HttpErrorHandler,
                                     override val messagesApi: MessagesApi
                                   ) extends Controller with I18nSupport {

  def setLanguage(lang: Lang): Action[AnyContent] = Action.async {
    implicit request =>

      if (langs.availables.exists(_.satisfies(lang))) {
        val url: String = request.headers.get(REFERER).getOrElse(routes.HomeController.index().url)
        Future.successful(Redirect(url).withLang(lang))
      } else {
        errorHandler.onClientError(request, BAD_REQUEST, "unrecognised language")
      }
  }
}
