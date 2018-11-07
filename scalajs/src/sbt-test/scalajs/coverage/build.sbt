lazy val root = kantanCrossProject("root").in(file("."))
  .settings(libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.3" % "test")
