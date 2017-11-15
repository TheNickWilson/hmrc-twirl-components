import play.core.PlayVersion

name := "hmrc-twirl-components"

version := "0.1"

scalaVersion := "2.11.12"

lazy val root = (project in file("."))
  .enablePlugins(SbtTwirl)
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play" % PlayVersion.current % "provided",
      "org.scalactic" %% "scalactic" % "3.0.4" % "test",
      "org.scalatest" %% "scalatest" % "3.0.4" % "test",
      "org.jsoup" % "jsoup" % "1.10.3" % "test",
      "org.mockito" % "mockito-core" % "2.12.0" % "test"
    ),
    TwirlKeys.templateImports ++= Seq(
      "play.api.mvc._",
      "play.api.data._",
      "play.api.i18n._",
      "play.api.templates.PlayMagic._"
    ),
    (unmanagedSourceDirectories in Test) +=
      baseDirectory.value / "src" / "test" / "twirl"
  )