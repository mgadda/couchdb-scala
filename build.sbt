import AssemblyKeys._

assemblySettings


name := "couchdb-scala"

version := "1.0"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-library" % "2.10.3",
  "com.twitter" %% "util-eval" % "6.5.0",
  "net.liftweb" %% "lift-json" % "2.5")
