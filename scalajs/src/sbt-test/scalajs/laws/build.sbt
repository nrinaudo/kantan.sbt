lazy val core = kantanCrossProject("core")
  .laws("laws")
  .settings(libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" % "test")

lazy val laws = kantanCrossProject("laws")
  .dependsOn(core)
