import sbt._
import Keys._
import Keys.{ `package` => pack }

object NinnyBotBuild extends Build {
  import Dependencies._

  lazy val root = Project(
    id = "ninnybot",
    base = file("."),
    settings = Project.defaultSettings ++ botSettings)

  def botSettings: Seq[Def.Setting[_]] = Seq(
    version       := "0.1-SNAPSHOT",
    organization  := "com.seriuszkierat.ninnybot",

    scalaVersion  := "2.10.2",
    scalacOptions ++= Seq("-deprecation", "-unchecked", "-optimise", "-explaintypes"),

    resolvers     ++= Dependencies.resolutionRepositories,

    libraryDependencies ++= Seq(
      Test.scalatest
    ),

    javaOptions += "-Xmx1g",

    // default parameters for scalatron play goal
    scalatronDir := file("/opt/scalatron-1.0.0.2"),
    headless := false,
    steps := 5000,
    maxSlaves := 650,

    play <<= (scalatronDir, name, javaOptions, pack in Compile, headless, steps, maxSlaves) map {
      (base, name, javaOptions, botJar, headless, steps, maxSlaves) =>
        require(base exists, "The setting '%s' must point to the base directory of an existing Scalatron installation.".format(scalatronDir.key.label))
        IO delete (base / "bots" / name)
        IO copyFile (botJar, base / "bots" / name / "ScalatronBot.jar")

        val headlessOpts = if (headless) Seq("-maxfps", "1000", "-headless", "yes") else Seq()

        Process("java" +: (javaOptions ++ Seq("-Ddebug=true", "-jar", "Scalatron.jar", "-browser", "no",
          "-x", "100", "-y", "100", "-steps", steps.toString, "-maxslaves", maxSlaves.toString) ++ headlessOpts), base / "bin") !
    }
  )

  val headless = SettingKey[Boolean]("headless", "whether the frontend should be started, or if we should just run in headless mode")
  val maxSlaves = SettingKey[Int]("maxSlaves", "maximum number of mini-bots to allow per scalatron master")
  val steps = SettingKey[Int]("steps", "number of simulation steps to make before the apocalypse")
  val scalatronDir = SettingKey[File]("scalatron-dir", "base directory of an existing Scalatron installation")

  val play = TaskKey[Unit]("play", "recompiles, packages and installs the bot, then starts Scalatron")
}

object Dependencies {
  val resolutionRepositories = Seq(
    "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
  )

  object V {
    val specs2        = "2.2.2"
    val scalatest     = "1.9.2"
    val pegdown       = "1.4.1"
    val slf4j         = "1.7.5"
    val logback       = "1.0.13"
    val scalalogging  = "1.0.1"
  }

  object Test {
    val specs2      = "org.specs2"                %% "specs2"          % V.specs2     %  "test"
    val scalatest   = "org.scalatest"             %% "scalatest"       % V.scalatest  %  "test"
    val pegdown     = "org.pegdown"               %% "pegdown"         % V.pegdown    %  "test"
  }

  object Container {
    val scalalogging  = "com.typesafe"              %%  "scalalogging-slf4j"    % V.scalalogging
    val slf4j         = "org.slf4j"                 %   "slf4j-api"             % V.slf4j
    val logback       = "ch.qos.logback"            %   "logback-classic"       % V.logback
  }
}