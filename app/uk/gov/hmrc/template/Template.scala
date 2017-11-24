package uk.gov.hmrc.template

import javax.inject.Inject

import controllers.AssetsBuilder
import play.api.http.HttpErrorHandler

class Template @Inject() (errorHandler: HttpErrorHandler) extends AssetsBuilder(errorHandler)
