lazy val core = project
  .laws("laws")

lazy val laws = project
  .dependsOn(core)
