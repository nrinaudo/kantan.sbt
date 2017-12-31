import com.lucidchart.sbt.scalafmt.ScalafmtCorePlugin.autoImport._
import com.lucidchart.sbt.scalafmt.ScalafmtSbtPlugin.autoImport._
import de.heikoseeberger.sbtheader.AutomateHeaderPlugin
import sbt._, Keys._
import sbt.plugins.JvmPlugin
import sbtrelease.ReleasePlugin.autoImport._, ReleaseTransformations._
import wartremover.{Wart, WartRemover, Warts}

import sbt.ScriptedPlugin.autoImport._

object BuildPlugin extends AutoPlugin {
  override def trigger = allRequirements

  override lazy val projectSettings = baseSettings ++ wartRemoverSettings ++ releaseSettings

  def releaseSettings: Seq[Setting[_]] =
    Seq(
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

  def baseSettings: Seq[sbt.Def.Setting[_]] =
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
      ),
      scriptedLaunchOpts ++= Seq("-Xmx1024M", "-Dplugin.version=" + version.value),
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
}
