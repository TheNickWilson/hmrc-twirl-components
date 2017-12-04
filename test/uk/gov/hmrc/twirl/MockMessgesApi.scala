package uk.gov.hmrc.twirl

import play.api.i18n.{Lang, MessagesApi}
import play.api.mvc.{RequestHeader, Result}
import play.mvc.Http

object MockMessagesApi extends MessagesApi {

  override def messages = ???
  override def preferred(candidates: Seq[Lang]) = ???
  override def preferred(request: RequestHeader) = ???
  override def preferred(request: Http.RequestHeader) = ???
  override def setLang(result: Result, lang: Lang) = ???
  override def clearLang(result: Result) = ???
  override def translate(key: String, args: Seq[Any])(implicit lang: Lang) = ???
  override def isDefinedAt(key: String)(implicit lang: Lang) = ???
  override def langCookieName = ???
  override def langCookieSecure = ???
  override def langCookieHttpOnly = ???

  override def apply(key: String, args: Any*)(implicit lang: Lang): String = {

    val argsString: String = if (args.nonEmpty) {
      s" args: ${args.mkString(" ")}"
    } else {
      ""
    }

    key + argsString
  }

  override def apply(keys: Seq[String], args: Any*)(implicit lang: Lang): String =
    apply(keys.head, args)
}
