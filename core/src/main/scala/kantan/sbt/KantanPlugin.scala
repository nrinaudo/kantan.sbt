/*
 * Copyright 2016 Nicolas Rinaudo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kantan.sbt

import com.github.tkawachi.doctest.DoctestPlugin.autoImport._
import de.heikoseeberger.sbtheader.HeaderPlugin
import sbt._, Keys._
import sbt.plugins.JvmPlugin
import tut.TutPlugin.autoImport._

/** Settings common to all projects.
  *
  * Among other things, this plugin will set sane scalac options, import whatever is required for the current version
  * of scala to support macros, ...
  *
  * It's usually necessary to enable one of the following plugins:
  *  - [[PublishedPlugin]]    : configures projects whose artifacts are intended for publication on maven central.
  *  - [[UnpublishedPlugin]]  : configures projects whose artifacts are not meant to be published, such as tests and
  *                             documentation.
  *  - [[DocumentationPlugin]]: configures projects whose output is a documentation website.
  */
object KantanPlugin extends AutoPlugin {

  /** Hack to let you work around the fact that SBT refuses scenarios like:
    *
    * - project `a`
    * - project `tests` depends on `a` and provides useful tools, such as scalacheck Arbitrary instances
    * - project `a` depends on `tests` in its `Test` configuration
    *
    * This is perfectly legal, but not supported. You can, however, use `.laws("tests")` in project `a` to
    * enable it.
    */
  def setLaws(name: String): Setting[Task[Classpath]] =
    unmanagedClasspath in Test ++= (fullClasspath in (LocalProject(name), Compile)).value

  object autoImport {

    /** `true` if java 8 is supported, `false` otherwise. */
    lazy val java8Supported: Boolean = BuildProperties.java8Supported

    implicit class KantanOperations(val proj: Project) extends AnyVal {

      def laws(name: String): Project =
        proj.settings(setLaws(name))

      def aggregateIf(predicate: Boolean)(refs: ProjectReference*): Project =
        if(predicate) proj.aggregate(refs: _*)
        else proj

      def dependsOnIf(predicate: Boolean)(refs: ClasspathDep[ProjectReference]*): Project =
        if(predicate) proj.dependsOn(refs: _*)
        else proj
    }

    val checkStyle: TaskKey[Unit]                = taskKey[Unit]("run all style checks")
    val kindProjectorVersion: SettingKey[String] = settingKey[String]("version of kind-projector to use")

  }

  import autoImport._

  override def trigger = allRequirements

  override def requires = JvmPlugin && HeaderPlugin

  override lazy val projectSettings = generalSettings ++ scalacSettings ++ javacSettings ++ commonDependencies ++
    inConfig(Compile)(checkStyleSettings) ++ inConfig(Test)(checkStyleSettings)

  /** By default, `checkStyle` does nothing. Other modules, such as scalafmt and scalastyle, plug in to that. */
  private def checkStyleSettings: Seq[Setting[_]] = Seq(
    checkStyle := {}
  )

  override def globalSettings: Seq[Setting[_]] =
    addCommandAlias(
      "validate",
      ";clean;checkStyle;test:checkStyle;coverageOn;test;coverageReport;coverageAggregate;coverageOff;doc"
    )

  /** General settings. */
  lazy val generalSettings: Seq[Setting[_]] = {
    Seq(
      // This is unpleasant, especially since it means there's no easy way to know whether we're running on an outdated
      // version. I haven't yet found a workaround.
      kindProjectorVersion   := "0.9.6",
      scalaVersion           := { if(BuildProperties.java8Supported) "2.12.6" else "2.11.12" },
      autoAPIMappings        := true,
      doctestMarkdownEnabled := true,
      doctestTestFramework   := DoctestTestFramework.ScalaTest,
      resolvers ++= Seq(
        Resolver.sonatypeRepo("releases"),
        Resolver.sonatypeRepo("snapshots")
      )
    )
  }

  def javacSettings: Seq[Setting[_]] =
    Seq(
      // If we're running 2.12+, compile to 1.8 bytecode. Otherwise, 1.6.
      javacOptions := {
        val jvm = (CrossVersion.partialVersion(version.value) match {
          case Some((maj, min)) if maj > 2 || min >= 12 ⇒ "1.8"
          case _                                        ⇒ "1.6"
        })

        Seq("-source", jvm, "-target", jvm)
      }
    )

  /** Sane, version dependent scalac settings. */
  def scalacSettings: Seq[Setting[_]] = {
    def base(version: String) =
      Seq(
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
        "-Xfuture",
        "-Xlint:adapted-args",
        "-Xlint:by-name-right-associative",
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
        "-Ywarn-dead-code",
        "-Ywarn-inaccessible",
        "-Ywarn-infer-any",
        "-Ywarn-nullary-override",
        "-Ywarn-nullary-unit",
        "-Ywarn-numeric-widen",
        "-Ywarn-value-discard"
      ) ++ (CrossVersion.partialVersion(version) match {
        case Some((_, x)) if x >= 12 ⇒
          Seq(
            "-Ypartial-unification",
            "-Xlint:constant",
            "-Ywarn-extra-implicit",
            "-Ywarn-unused:implicits",
            "-Ywarn-unused:locals",
            "-Ywarn-unused:params",
            "-Ywarn-unused:patvars",
            "-Ywarn-unused:privates",
            "-Ywarn-unused:imports",
            "-Ybackend-parallelism",
            java.lang.Runtime.getRuntime().availableProcessors().toString
          )
        case Some((_, x)) if x == 11 ⇒
          Seq("-Ywarn-unused-import")
        case _ ⇒ Seq.empty
      })

    // Sane defaults for warnings / errors:
    // - disable unused imports from the REPL, since it basically make sit unusable
    // - disable unused imports from tut, since we don't want spurious warnings in our documentation
    // - nothing is fatal (use StrictKantanPlugin for that)
    val unusedImports = Seq("-Ywarn-unused:imports", "-Ywarn-unused-import")
    Seq(
      scalacOptions := base(scalaVersion.value),
      scalacOptions in (Compile, console) --= unusedImports,
      scalacOptions in Tut --= unusedImports
    )
  }

  /** Includes common dependencies (macros and kind-projector). */
  lazy val commonDependencies: Seq[Setting[_]] = Seq(
    libraryDependencies ++= Seq(
      compilerPlugin("org.spire-math" % "kind-projector" % kindProjectorVersion.value cross CrossVersion.binary),
      "org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided"
    )
  )
}
