lazy val core = kantanCrossProject("core")
  .laws("laws")

lazy val coreJVM = core.jvm
lazy val coreJS = core.js

lazy val laws = kantanCrossProject("laws")
  .dependsOn(core)

lazy val lawsJVM = laws.jvm
lazy val lawsJS = laws.js
