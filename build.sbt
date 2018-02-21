organization := "com.gu"
name := "pdfredactor"
version := "0.1"

scalaVersion := "2.12.4"
// Compiler settings. Use scalac -X for other options and their description.
// See Here for more info http://www.scala-lang.org/files/archive/nightly/docs/manual/html/scalac.html
scalacOptions ++= List("-feature","-deprecation", "-unchecked", "-Xlint")


libraryDependencies ++= Seq(
  "org.apache.pdfbox" % "pdfbox" % "2.0.8",
  "org.rogach" %% "scallop" % "3.1.1"
)
