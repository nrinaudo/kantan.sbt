lazy val root = Project(id = "kantan-sbt", base = file("."))
  .settings(moduleName := "root")
  .settings(
    publish         := {},
    publishLocal    := {},
    publishArtifact := false
  )
  .aggregate(docs, core, kantan, release, scalajs, scalafmt, scalastyle)

lazy val core = project
  .settings(
    moduleName := "kantan.sbt",
    name       := "core",
    sbtPlugin  := true
  )
  .enablePlugins(AutomateHeaderPlugin, SbtPlugin)
  .settings(
    addSbtPlugin("com.eed3si9n"        % "sbt-unidoc"      % Versions.sbtUnidoc),
    addSbtPlugin("com.github.tkawachi" % "sbt-doctest"     % Versions.sbtDoctest),
    addSbtPlugin("com.typesafe.sbt"    % "sbt-ghpages"     % Versions.sbtGhPages),
    addSbtPlugin("com.typesafe.sbt"    % "sbt-site"        % Versions.sbtSite),
    addSbtPlugin("de.heikoseeberger"   % "sbt-header"      % Versions.sbtHeader),
    addSbtPlugin("io.get-coursier"     % "sbt-coursier"    % Versions.coursier),
    addSbtPlugin("io.spray"            % "sbt-boilerplate" % Versions.boilerplate),
    addSbtPlugin("org.scalameta"       % "sbt-mdoc"        % Versions.mdoc),
    addSbtPlugin("org.scoverage"       %% "sbt-scoverage"  % Versions.scoverage),
    addSbtPlugin("org.wartremover"     % "sbt-wartremover" % Versions.wartRemover)
  )

lazy val release = project
  .settings(
    moduleName := "kantan.sbt-release",
    name       := "release",
    sbtPlugin  := true
  )
  .enablePlugins(AutomateHeaderPlugin, SbtPlugin)
  .settings(
    addSbtPlugin("com.github.gseitz" % "sbt-release" % Versions.sbtRelease)
  )
  .dependsOn(core)

lazy val scalastyle = project
  .settings(
    moduleName := "kantan.sbt-scalastyle",
    name       := "scalastyle",
    sbtPlugin  := true
  )
  .enablePlugins(AutomateHeaderPlugin, SbtPlugin)
  .settings(
    addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % Versions.scalastyle)
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
    addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % Versions.sbtSonatype),
    addSbtPlugin("com.jsuereth"   % "sbt-pgp"      % Versions.sbtPgp)
  )
  .dependsOn(core, release, scalafmt, scalastyle)

lazy val docs = project
  .enablePlugins(DocumentationPlugin)
