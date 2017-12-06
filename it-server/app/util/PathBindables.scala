package util

import play.api.i18n.Lang
import play.api.mvc.PathBindable

trait PathBindables {

  implicit def langPathBindable(implicit str: PathBindable[String]): PathBindable[Lang] =
    new PathBindable[Lang] {

      override def bind(key: String, value: String): Either[String, Lang] =
        str.bind(key, value).right.flatMap {
          lang =>
            Lang.get(lang).toRight("unrecognised language")
        }

      override def unbind(key: String, value: Lang): String =
        str.unbind(key, value.code)
    }
}

object PathBindables extends PathBindables