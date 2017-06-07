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
import com.github.tkawachi.doctest.DoctestPlugin.DoctestTestFramework
import com.typesafe.sbt.SbtGit.git
import de.heikoseeberger.sbtheader.HeaderPlugin
import de.heikoseeberger.sbtheader.HeaderPlugin.autoImport._
import org.scalastyle.sbt.ScalastylePlugin
import sbt._
import sbt.Keys._
import sbt.plugins.JvmPlugin
import scala.util.matching.Regex

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
  // - Common dependency versions --------------------------------------------------------------------------------------
  // -------------------------------------------------------------------------------------------------------------------
  val kindProjectorVersion = "0.9.4"
  val macroParadiseVersion = "2.1.0"



  // - Public settings -------------------------------------------------------------------------------------------------
  // -------------------------------------------------------------------------------------------------------------------
  object autoImport {
    /** `true` if java 8 is supported, `false` otherwise. */
    lazy val java8Supported: Boolean = BuildProperties.java8Supported

    implicit class KantanOperations(val proj: Project) extends AnyVal {
      def laws(name: String): Project =
        proj.settings(unmanagedClasspath in Test ++= (fullClasspath in (LocalProject(name), Compile)).value)

      def aggregateIf(predicate: Boolean)(refs: ProjectReference*): Project =
        if(predicate) proj.aggregate(refs:_*)
        else          proj

      def dependsOnIf(predicate: Boolean)(refs: ClasspathDep[ProjectReference]*): Project =
        if(predicate) proj.dependsOn(refs:_*)
        else          proj
    }
  }
  import autoImport._



  // - AutoPlugin implementation ---------------------------------------------------------------------------------------
  // -------------------------------------------------------------------------------------------------------------------
  override def trigger = allRequirements

  override def requires = JvmPlugin && HeaderPlugin

  override lazy val projectSettings =
    generalSettings ++ scalacSettings ++ commonDependencies ++
    ScalastylePlugin.projectSettings

  override def globalSettings =
    addCommandAlias("validate", ";clean;scalastyle;test:scalastyle;coverage;test;coverageReport;coverageAggregate;doc")



  // - Custom settings -------------------------------------------------------------------------------------------------
  // -------------------------------------------------------------------------------------------------------------------
  /** General settings. */
  lazy val generalSettings: Seq[Setting[_]] = {
    Seq(
      scalaVersion            := "2.12.2",
      autoAPIMappings         := true,
      incOptions              := incOptions.value.withNameHashing(true),
      doctestWithDependencies := false,
      doctestMarkdownEnabled  := true,
      doctestTestFramework    := DoctestTestFramework.ScalaTest,
      resolvers              ++= Seq(
        Resolver.sonatypeRepo("releases"),
        Resolver.sonatypeRepo("snapshots")
      )
    )
  }

  /** Sane, version dependent scalac settings. */
  def scalacSettings: Seq[Setting[_]] = {
    def base(version: String) = Seq(
      "-encoding", "UTF-8",
      "-feature",
      "-language:existentials",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-language:experimental.macros",
      "-deprecation",
      "-unchecked",
      "-Yno-adapted-args",
      "-Ywarn-dead-code",
      "-Ywarn-numeric-widen",
      "-Ywarn-value-discard",
      "-Xfuture"
    ) ++ (CrossVersion.partialVersion(version) match {
      case Some((_, x)) if x >= 12 ⇒ Seq("-Ypartial-unification")
      case Some((_, 10)) ⇒ Seq("-Xdivergence211")
      case _             ⇒ Seq.empty
    })

    // Sane defaults for warnings / errors:
    // - -Xlint is only enabled for Compile & Test, since it basically makes the REPL unusable.
    // - nothing is fatal (use StrictKantanPlugin for that)
    Seq(
      scalacOptions                       := base(scalaVersion.value),
      scalacOptions in (Compile, compile) += "-Xlint",
      scalacOptions in Test               += "-Xlint"
    )
  }


  /** Includes common dependencies (macros and kind-projector). */
  lazy val commonDependencies: Seq[Setting[_]] = Seq(
    libraryDependencies ++= Seq(
      compilerPlugin("org.spire-math" % "kind-projector" % kindProjectorVersion cross CrossVersion.binary),
      "org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided"
    ) ++ {
      if(scalaVersion.value.startsWith("2.10"))
        List(compilerPlugin("org.scalamacros" % "paradise" % macroParadiseVersion cross CrossVersion.full))
      else Nil
    }
  )
}
