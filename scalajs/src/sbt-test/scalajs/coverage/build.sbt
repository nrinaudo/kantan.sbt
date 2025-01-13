lazy val root = kantanCrossProject("root").in(file("."))
  .settings(libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" % Test)
