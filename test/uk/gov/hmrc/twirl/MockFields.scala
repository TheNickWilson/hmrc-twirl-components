package uk.gov.hmrc.twirl

import org.mockito.Mockito.when
import play.api.data.{Field, FormError}
import org.scalatest.mockito.MockitoSugar

trait MockFields extends MockitoSugar {

  def normalField: Field = {

    val field = mock[Field]

    when(field.id) thenReturn "my_id"
    when(field.name) thenReturn "my.name"
    when(field.value) thenReturn None
    when(field.hasErrors) thenReturn false
    when(field.errors) thenReturn Seq.empty

    field
  }

  def fieldWithValue(v: String): Field = {

    val field = mock[Field]

    when(field.id) thenReturn "my_id"
    when(field.name) thenReturn "my.name"
    when(field.value) thenReturn Some(v)
    when(field.hasErrors) thenReturn false
    when(field.errors) thenReturn Seq.empty

    field
  }

  def fieldWithError: Field = {

    val field = mock[Field]

    when(field.id) thenReturn "my_id"
    when(field.name) thenReturn "my.name"
    when(field.value) thenReturn None
    when(field.hasErrors) thenReturn true
    when(field.errors) thenReturn Seq(
      FormError("my.name", "some error")
    )

    field
  }
}
