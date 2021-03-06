name := """Appathon"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.10.4"

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.3.0-2",
  "com.typesafe.play.plugins" %% "play-plugins-mailer" % "2.3.1",
  "com.typesafe.slick" %% "slick" % "2.1.0",
  "org.postgresql" % "postgresql" % "9.4-1200-jdbc41"
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)
