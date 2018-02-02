lazy val root = kantanCrossProject("root").in(file("."))
  .enablePlugins(BoilerplatePlugin)

lazy val rootJVM = root.jvm
lazy val rootJS = root.js
