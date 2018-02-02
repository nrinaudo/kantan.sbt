lazy val root = kantanCrossProject("root").in(file("."))
  .settings(libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.3" % "test")

lazy val rootJVM = root.jvm
lazy val rootJS = root.js
