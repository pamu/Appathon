name := """Appathon"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.10.4"

javacOptions ++= Seq("-source", "1.7", "-target", "1.7")

libraryDependencies ++= Seq(
  "org.webjars" %% "webjars-play" % "2.3.0-2"
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)
