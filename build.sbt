import de.heikoseeberger.sbtheader.AutomateHeaderPlugin
import ReleaseTransformations._

def wartRemoverSettings: Seq[Setting[_]] =
  List(Compile, Test).flatMap { c ⇒
    inConfig(c)(
      WartRemover.autoImport.wartremoverErrors in (Compile, compile) ++=
        Warts.allBut(
          Wart.NonUnitStatements,
          Wart.Equals,
          Wart.Overloading,
          Wart.ImplicitParameter,
          Wart.Nothing,
          Wart.ImplicitConversion,
          Wart.Any,
          Wart.PublicInference,
          Wart.Recursion
        )
    )
  }

lazy val baseSettings: Seq[sbt.Def.Setting[_]] =
  Seq(
    organization         := "com.nrinaudo",
    organizationHomepage := Some(url("https://nrinaudo.github.io")),
    organizationName     := "Nicolas Rinaudo",
    startYear            := Some(2016),
    scalaVersion         := "2.12.4",
    licenses             := Seq("Apache-2.0" → url("https://www.apache.org/licenses/LICENSE-2.0.html")),
    homepage             := Some(url(s"https://nrinaudo.github.io/kantan.sbt")),
    scalafmtVersion      := Versions.scalafmt,
    publishTo := Some(
      if(isSnapshot.value)
        Opts.resolver.sonatypeSnapshots
      else
        Opts.resolver.sonatypeStaging
    ),
    developers := List(
      Developer("nrinaudo", "Nicolas Rinaudo", "nicolas@nrinaudo.com", url("https://twitter.com/nicolasrinaudo"))
    ),
    scmInfo := Some(
      ScmInfo(
        url(s"https://github.com/nrinaudo/kantan.sbt"),
        s"scm:git:git@github.com:nrinaudo/kantan.sbt.git"
      )
    )
  ) ++ wartRemoverSettings

lazy val pluginSettings = Seq(
  scriptedLaunchOpts ++= Seq("-Xmx1024M", "-Dplugin.version=" + version.value),
  sbtPlugin          := true,
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding",
    "utf-8",
    "-explaintypes",
    "-feature",
    "-language:existentials",
    "-language:experimental.macros",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-unchecked",
    "-Xcheckinit",
    "-Xfatal-warnings",
    "-Xfuture",
    "-Xlint:adapted-args",
    "-Xlint:by-name-right-associative",
    "-Xlint:constant",
    "-Xlint:delayedinit-select",
    "-Xlint:doc-detached",
    "-Xlint:inaccessible",
    "-Xlint:infer-any",
    "-Xlint:missing-interpolator",
    "-Xlint:nullary-override",
    "-Xlint:nullary-unit",
    "-Xlint:option-implicit",
    "-Xlint:package-object-classes",
    "-Xlint:poly-implicit-overload",
    "-Xlint:private-shadow",
    "-Xlint:stars-align",
    "-Xlint:type-parameter-shadow",
    "-Xlint:unsound-match",
    "-Yno-adapted-args",
    "-Ypartial-unification",
    "-Ywarn-dead-code",
    "-Ywarn-extra-implicit",
    "-Ywarn-inaccessible",
    "-Ywarn-infer-any",
    "-Ywarn-nullary-override",
    "-Ywarn-nullary-unit",
    "-Ywarn-numeric-widen",
    "-Ywarn-unused:implicits",
    "-Ywarn-unused:imports",
    "-Ywarn-unused:locals",
    "-Ywarn-unused:params",
    "-Ywarn-unused:patvars",
    "-Ywarn-unused:privates",
    "-Ywarn-value-discard"
  )
)

lazy val root = Project(id = "kantan-sbt", base = file("."))
  .settings(moduleName := "root")
  .settings(baseSettings)
  .settings(
    releaseProcess := Seq[ReleaseStep](
      checkSnapshotDependencies,
      inquireVersions,
      runClean,
      releaseStepCommand("scalastyle"),
      releaseStepCommand("scalafmt"),
      releaseStepCommand("sbt:scalafmt"),
      releaseStepCommand("scripted"),
      setReleaseVersion,
      commitReleaseVersion,
      tagRelease,
      releaseStepCommand("publishSigned"),
      setNextVersion,
      commitNextVersion,
      releaseStepCommand("sonatypeReleaseAll"),
      pushChanges
    )
  )
  .settings(
    publish         := {},
    publishLocal    := {},
    publishArtifact := false
  )
  .aggregate(core, kantan)

lazy val core = project
  .settings(
    moduleName := "kantan.sbt",
    name       := "core"
  )
  .settings(baseSettings)
  .settings(pluginSettings)
  .enablePlugins(AutomateHeaderPlugin)
  .settings(
    addSbtPlugin("com.eed3si9n"        % "sbt-unidoc"             % Versions.sbtUnidoc),
    addSbtPlugin("com.github.gseitz"   % "sbt-release"            % Versions.sbtRelease),
    addSbtPlugin("com.github.tkawachi" % "sbt-doctest"            % Versions.sbtDoctest),
    addSbtPlugin("com.lucidchart"      % "sbt-scalafmt"           % Versions.sbtScalafmt),
    addSbtPlugin("com.typesafe.sbt"    % "sbt-ghpages"            % Versions.sbtGhPages),
    addSbtPlugin("com.typesafe.sbt"    % "sbt-site"               % Versions.sbtSite),
    addSbtPlugin("de.heikoseeberger"   % "sbt-header"             % Versions.sbtHeader),
    addSbtPlugin("io.spray"            % "sbt-boilerplate"        % Versions.boilerplate),
    addSbtPlugin("org.scalastyle"      %% "scalastyle-sbt-plugin" % Versions.scalastyle),
    addSbtPlugin("org.scoverage"       %% "sbt-scoverage"         % Versions.scoverage),
    addSbtPlugin("org.tpolecat"        % "tut-plugin"             % Versions.tut),
    addSbtPlugin("org.wartremover"     % "sbt-wartremover"        % Versions.wartRemover)
  )

lazy val kantan = project
  .settings(
    moduleName := "kantan.sbt-kantan",
    name       := "kantan"
  )
  .settings(baseSettings)
  .settings(pluginSettings)
  .enablePlugins(AutomateHeaderPlugin)
  .settings(
    addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % Versions.sbtSonatype),
    addSbtPlugin("com.jsuereth"   % "sbt-pgp"      % Versions.sbtPgp)
  )
  .dependsOn(core)

addCommandAlias(
  "validate",
  ";clean;scalastyle;test:scalastyle;scalafmt::test;test:scalafmt::test;sbt:scalafmt::test;compile;scripted"
)
