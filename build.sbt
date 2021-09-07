import org.jetbrains.sbtidea.Keys._
import sbt.Keys._

lazy val quickPatch =
  project
    .withId("quick-patch").in(file("."))
    .settings(
      name := "quick-patch",
      organization := "com.daodecode",
      version := "0.1.2",
      scalaVersion := "2.13.6",
      scalacOptions := Seq(
        "-Xlint",
        "-unchecked",
        "-deprecation",
        "-Xfatal-warnings",
        "-Ywarn-dead-code",
        "-feature",
        "-language:postfixOps",
        "-language:implicitConversions",
        "-Ywarn-unused",
        "-encoding", "UTF-8"
      ),
      libraryDependencies ++=
        Seq(
          "org.scala-lang.modules" %% "scala-swing" % "3.0.0",
          "org.scalatest" %% "scalatest" % "3.2.9" % "test"
        ),
      intellijPluginName := "QuickPatch",
      intellijBuild := "2021.2.1",
      packageLibraryMappings ++= Seq(
        "org.scala-lang" % "scala-library" % ".*" -> Some("lib/scala-library.jar"),
        "org.scala-lang.modules" % "scala-swing" % ".*" -> Some("lib/scala-swing.jar")
      )
    ).enablePlugins(SbtIdeaPlugin)

