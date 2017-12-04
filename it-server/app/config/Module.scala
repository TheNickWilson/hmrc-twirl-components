package config

import play.api.inject.Binding
import play.api.{Configuration, Environment}
import uk.gov.hmrc.viewmodels.layout.LayoutConfiguration

class Module extends play.api.inject.Module {

  override def bindings(environment: Environment, configuration: Configuration): Seq[Binding[_]] = Seq(
    bind[LayoutConfiguration].to[LayoutConfigurationImpl]
  )
}
