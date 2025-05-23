import de.heikoseeberger.sbtheader.AutomateHeaderPlugin
import sbt._, Keys._
import sbt.plugins.{JvmPlugin, SbtPlugin}
import sbt.ScriptedPlugin.autoImport._
import sbtrelease.ReleasePlugin, ReleasePlugin.autoImport._, ReleaseTransformations._, ReleaseKeys._
import wartremover.{Wart, WartRemover, Warts}
import scalafix.sbt.ScalafixPlugin.autoImport.scalafixSemanticdb

object LocalBuildPlugin extends AutoPlugin {
  override def trigger =
    allRequirements

  override def requires =
    JvmPlugin && ReleasePlugin

  override lazy val projectSettings = baseSettings ++ wartRemoverSettings ++ releaseSettings

  override def globalSettings: Seq[Setting[_]] =
    addCommandAlias(
      "validate",
      "; clean"
        + "; scalafix --check"
        + "; scalafmtSbtCheck"
        + "; scalafmtCheck"
        + "; Test / scalafix --check"
        + "; Test / scalafmtCheck"
        + "; compile"
        + "; scripted"
    )

  lazy val runScripted: ReleaseStep = {
    val scriptedStep = releaseStepInputTask(scripted)
    ReleaseStep(
      action = { st: State =>
        if(!st.get(skipTests).getOrElse(false)) {
          scriptedStep(st)
        } else st
      }
    )
  }

  def releaseSettings: Seq[Setting[_]] =
    Seq(
      releaseProcess := Seq[ReleaseStep](
        checkSnapshotDependencies,
        inquireVersions,
        runClean,
        releaseStepCommand("scalafix --check"),
        releaseStepCommand("scalafmtCheck"),
        releaseStepCommand("scalafmtSbtCheck"),
        runScripted,
        setReleaseVersion,
        commitReleaseVersion,
        tagRelease,
        releaseStepCommand("publishSigned"),
        releaseStepCommand("sonatypeReleaseAll"),
        releaseStepCommand("makeSite"),
        releaseStepCommand("ghpagesPushSite"),
        setNextVersion,
        commitNextVersion,
        pushChanges
      )
    )

  def wartRemoverSettings: Seq[Setting[_]] =
    List(Compile, Test).flatMap { c =>
      inConfig(c)(
        Compile / compile / WartRemover.autoImport.wartremoverErrors ++=
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

  def baseSettings: Seq[sbt.Def.Setting[_]] =
    Seq(
      organization         := "com.nrinaudo",
      organizationHomepage := Some(url("https://nrinaudo.github.io")),
      organizationName     := "Nicolas Rinaudo",
      startYear            := Some(2016),
      scalaVersion         := "2.12.20",
      licenses             := Seq("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0.html")),
      homepage             := Some(url(s"https://nrinaudo.github.io/kantan.sbt")),
      versionScheme        := Some("early-semver"),
      publishTo := Some(
        if(isSnapshot.value)
          Opts.resolver.sonatypeOssSnapshots.head
        else
          Opts.resolver.sonatypeStaging
      ),
      developers := List(
        Developer("nrinaudo", "Nicolas Rinaudo", "nicolas@nrinaudo.com", url("https://twitter.com/nicolasrinaudo")),
        Developer(
          "joriscode",
          "Joris",
          "2750485+joriscode@users.noreply.github.com",
          url("https://github.com/joriscode")
        )
      ),
      scmInfo := Some(
        ScmInfo(
          url(s"https://github.com/nrinaudo/kantan.sbt"),
          s"scm:git:git@github.com:nrinaudo/kantan.sbt.git"
        )
      ),
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
        "-Ywarn-value-discard",
        "-Ybackend-parallelism",
        java.lang.Runtime.getRuntime().availableProcessors().toString
      ),
      // Enable scalafix semantic rules
      semanticdbEnabled      := true,
      semanticdbIncludeInJar := false,
      semanticdbVersion      := scalafixSemanticdb.revision
    )
}

object SbtLocalBuildPlugin extends AutoPlugin {
  override def trigger =
    allRequirements

  override def requires =
    SbtPlugin

  override lazy val projectSettings = Seq(
    scriptedLaunchOpts ++= Seq("-Xmx1024M", s"-Dplugin.version=${version.value}"),
    scriptedBufferLog   := false
  )
}
