lazy val core = kantanCrossProject("core")
  .laws("laws")

lazy val laws = kantanCrossProject("laws")
  .dependsOn(core)
