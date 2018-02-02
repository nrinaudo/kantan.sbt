lazy val root = kantanCrossProject("root").in(file("."))

lazy val rootJVM = root.jvm
lazy val rootJS = root.js
