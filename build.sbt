import org.jetbrains.sbtidea.Keys._
import sbt.Keys._

lazy val quickPatch =
  project
    .withId("quick-patch").in(file("."))
    .settings(
      name := "quick-patch",
      organization := "com.daodecode",
      scalaVersion := "2.12.8",
      scalacOptions := Seq(
        "-Xlint",
        "-unchecked",
        "-deprecation",
        "-Xfatal-warnings",
        "-Ywarn-inaccessible",
        "-Ywarn-dead-code",
        "-Ywarn-adapted-args",
        "-Ywarn-nullary-unit",
        "-feature",
        "-language:postfixOps",
        "-language:implicitConversions",
        "-Ywarn-unused",
        "-Ywarn-unused-import",
        "-encoding", "UTF-8"
      ),
      libraryDependencies ++=
        Seq(
          "org.scala-lang.modules" %% "scala-swing" % "2.1.0",
          "org.scalatest" %% "scalatest" % "3.0.6" % "test"
        ),
      ideaPluginName in ThisBuild := "QuickPatch",
      ideaBuild := "2018.1.1",
      ideaPublishSettings := PublishSettings(pluginId = "", username = "", password = "", channel = None),
      packageLibraryMappings ++= Seq(
        "org.scala-lang" % "scala-library" % ".*" -> Some("lib/scala-library.jar"),
        "org.scala-lang.modules" % "scala-swing" % ".*" -> Some("lib/scala-swing.jar")
      )
    ).enablePlugins(SbtIdeaPlugin)

lazy val ideaRunner = createRunnerProject(quickPatch, "idea-runner")


