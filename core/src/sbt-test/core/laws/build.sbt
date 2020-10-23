lazy val core = project
  .laws("laws")
  .settings(libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" % "test")

lazy val laws = project
  .dependsOn(core)
