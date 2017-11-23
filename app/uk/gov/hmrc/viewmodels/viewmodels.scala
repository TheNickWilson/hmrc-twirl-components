package uk.gov.hmrc

import play.api.data.Field

package object viewmodels {

  implicit class RichField(field: Field) {

    def values: Seq[String] = {
      field.value.toSeq ++ field.indexes.flatMap {
        i =>
          field(s"[$i]").value
      }
    }
  }
}
