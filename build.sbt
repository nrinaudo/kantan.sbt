lazy val root = Project(id = "kantan-sbt", base = file("."))
  .settings(moduleName := "root")
  .settings(
    publish         := {},
    publishLocal    := {},
    publishArtifact := false
  )
  .aggregate(docs, core, kantan, release, scalajs, scalafix, scalafmt)

lazy val core = project
  .settings(
    moduleName := "kantan.sbt",
    name       := "core",
    sbtPlugin  := true
  )
  .enablePlugins(AutomateHeaderPlugin, SbtPlugin)
  .settings(
    addSbtPlugin("com.github.sbt"        % "sbt-boilerplate" % Versions.boilerplate),
    addSbtPlugin("com.github.sbt"        % "sbt-ghpages"     % Versions.sbtGhPages),
    addSbtPlugin("com.github.sbt"        % "sbt-site"        % Versions.sbtSite),
    addSbtPlugin("com.github.sbt"        % "sbt-unidoc"      % Versions.sbtUnidoc),
    addSbtPlugin("de.heikoseeberger"     % "sbt-header"      % Versions.sbtHeader),
    addSbtPlugin("io.github.sbt-doctest" % "sbt-doctest"     % Versions.sbtDoctest),
    addSbtPlugin("org.scoverage"        %% "sbt-scoverage"   % Versions.scoverage),
    addSbtPlugin("org.wartremover"       % "sbt-wartremover" % Versions.wartRemover),
    addSbtPlugin(
      ("org.scalameta" % "sbt-mdoc" % Versions.mdoc)
        .excludeAll(ExclusionRule("com.github.plokhotnyuk.jsoniter-scala"))
    )
  )

lazy val release = project
  .settings(
    moduleName := "kantan.sbt-release",
    name       := "release",
    sbtPlugin  := true
  )
  .enablePlugins(AutomateHeaderPlugin, SbtPlugin)
  .settings(
    addSbtPlugin("com.github.sbt" % "sbt-release" % Versions.sbtRelease)
  )
  .dependsOn(core)
  .dependsOn(scalafmt) // ensure that kantan.sbt-scalafmt is released locally for scripted tests

lazy val scalafix = project
  .settings(
    moduleName := "kantan.sbt-scalafix",
    name       := "scalafix",
    sbtPlugin  := true
  )
  .enablePlugins(AutomateHeaderPlugin, SbtPlugin)
  .settings(
    addSbtPlugin("ch.epfl.scala." % "sbt-scalafix" % Versions.sbtScalafix)
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
    addSbtPlugin(
      ("org.scalameta" % "sbt-scalafmt" % Versions.sbtScalafmt)
        .excludeAll(ExclusionRule("com.github.plokhotnyuk.jsoniter-scala"))
    )
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
    addSbtPlugin("com.github.sbt" % "sbt-pgp"      % Versions.sbtPgp)
  )
  .dependsOn(core, release, scalafix, scalafmt)

lazy val docs = project
  .enablePlugins(LocalDocumentationPlugin)
