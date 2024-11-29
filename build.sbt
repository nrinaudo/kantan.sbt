lazy val root = Project(id = "kantan-sbt", base = file("."))
  .settings(moduleName := "root")
  .settings(
    publish         := {},
    publishLocal    := {},
    publishArtifact := false
  )
  .aggregate( /*docs,*/ core, kantan, release, scalajs, scalafmt)

lazy val core = project
  .settings(
    moduleName := "kantan.sbt",
    name       := "core",
    sbtPlugin  := true
  )
  .enablePlugins(AutomateHeaderPlugin, SbtPlugin)
  .settings(
    libraryDependencySchemes += "com.lihaoyi" %% "geny" % VersionScheme.Always,
    addSbtPlugin("com.github.tkawachi" % "sbt-doctest"     % Versions.sbtDoctest exclude ("com.lihaoyi", "geny")),
    addSbtPlugin("de.heikoseeberger"   % "sbt-header"      % Versions.sbtHeader),
    addSbtPlugin("com.github.sbt"      % "sbt-boilerplate" % Versions.boilerplate),
    addSbtPlugin("org.scoverage"       %% "sbt-scoverage"  % Versions.scoverage)
    // addSbtPlugin("com.eed3si9n"        % "sbt-unidoc"      % Versions.sbtUnidoc),
    // addSbtPlugin("com.typesafe.sbt"    % "sbt-ghpages"     % Versions.sbtGhPages),
    // addSbtPlugin("com.typesafe.sbt"    % "sbt-site"        % Versions.sbtSite),
    // addSbtPlugin("org.scalameta"       % "sbt-mdoc"        % Versions.mdoc),
    // addSbtPlugin("com.github.sbt"      % "sbt-git"         % "2.1.0")
  )

lazy val release = project
  .settings(
    moduleName := "kantan.sbt-release",
    name       := "release",
    sbtPlugin  := true
  )
  .enablePlugins(AutomateHeaderPlugin, SbtPlugin)
  .settings(
    libraryDependencySchemes += "com.lihaoyi" %% "geny" % VersionScheme.Always,
    addSbtPlugin("com.github.sbt" % "sbt-release" % Versions.sbtRelease)
  )
  .dependsOn(core)

lazy val scalafmt = project
  .settings(
    moduleName := "kantan.sbt-scalafmt",
    name       := "scalafmt",
    sbtPlugin  := true
  )
  .enablePlugins(AutomateHeaderPlugin, SbtPlugin)
  .settings(
    libraryDependencySchemes += "com.lihaoyi" %% "geny" % VersionScheme.Always,
    addSbtPlugin("org.scalameta" % "sbt-scalafmt" % Versions.sbtScalafmt)
  )
  .dependsOn(core)

lazy val scalajs = project
  .settings(
    moduleName := "kantan.sbt-scalajs",
    name       := "scalajs",
    sbtPlugin  := true
  )
  .enablePlugins(AutomateHeaderPlugin, SbtPlugin)
  .settings(
    libraryDependencySchemes += "com.lihaoyi" %% "geny" % VersionScheme.Always,
    addSbtPlugin("org.scala-js"       % "sbt-scalajs"              % Versions.scalajs),
    addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % Versions.scalajsCross)
  )
  .dependsOn(core)

lazy val kantan = project
  .settings(
    moduleName := "kantan.sbt-kantan",
    name       := "kantan",
    sbtPlugin  := true
  )
  .enablePlugins(AutomateHeaderPlugin, SbtPlugin)
  .settings(
    libraryDependencySchemes += "com.lihaoyi" %% "geny" % VersionScheme.Always,
    // addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % Versions.sbtSonatype),
    addSbtPlugin("com.github.sbt" % "sbt-pgp" % Versions.sbtPgp)
  )
  .dependsOn(core, release, scalafmt)

// lazy val docs = project
// .enablePlugins(DocumentationPlugin)
