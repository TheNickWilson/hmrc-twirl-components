import play.core.PlayVersion

resolvers += Resolver.bintrayRepo("hmrc", "releases")

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, SbtWeb)
  .settings(
    name := "hmrc-twirl-components",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := "2.11.12",
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play" % PlayVersion.current % "provided",
      "com.typesafe.play" %% "filters-helpers" % PlayVersion.current % "provided",
      "org.scalactic" %% "scalactic" % "3.0.4" % "test",
      "org.scalatest" %% "scalatest" % "3.0.4" % "test",
      "org.jsoup" % "jsoup" % "1.10.3" % "test",
      "org.mockito" % "mockito-core" % "2.12.0" % "test",
      "com.typesafe.play" %% "play-test" % PlayVersion.current % "test",
      "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.1" % "test"
    ),
    TwirlKeys.templateImports ++= Seq(
      "play.api.Configuration",
      "uk.gov.hmrc.template.routes",
      "uk.gov.hmrc.viewmodels._"
    ),
    (sourceDirectories in (Compile, TwirlKeys.compileTemplates)) +=
      baseDirectory.value / "govuk_template_play" / "views" / "layouts",
    (unmanagedResourceDirectories in Compile) +=
      baseDirectory.value / "govuk_template_play" / "assets"
  )

lazy val itServer = (project in file("it-server"))
  .enablePlugins(PlayScala)
  .dependsOn(root)
  .settings(
    name := "it-server",
    scalaVersion := "2.11.12",
    TwirlKeys.templateImports ++= Seq(
      "uk.gov.hmrc.twirl.html._",
      "uk.gov.hmrc.viewmodels._"
    ),
    routesImport ++= Seq(
      "play.api.i18n._",
      "util.PathBindables._"
    ),
    libraryDependencies +=
      "com.typesafe.play" %% "filters-helpers" % PlayVersion.current
  )
