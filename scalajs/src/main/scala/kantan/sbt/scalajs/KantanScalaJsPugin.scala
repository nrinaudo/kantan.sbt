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
package scalajs

import KantanPlugin.setLaws
import com.github.tkawachi.doctest.DoctestPlugin.autoImport._
import org.scalajs.sbtplugin.cross.{CrossProject, CrossType}
import sbt._, Keys._
import scoverage.ScoverageKeys.coverageEnabled
import spray.boilerplate.BoilerplatePlugin.autoImport.boilerplateSource

object KantanScalaJsPlugin extends AutoPlugin {

  override def trigger = allRequirements

  override def requires = KantanPlugin

  object autoImport {

    def kantanCrossProject(id: String): CrossProject =
      CrossProject(jvmId = id + "-jvm", jsId = id + "-js", file(id), CrossType.Full)
      // Overrides the default sbt-boilerplate source directory: https://github.com/sbt/sbt-boilerplate/issues/21
        .settings(
          boilerplateSource in Compile := baseDirectory.value.getParentFile / "shared" / "src" / "main" / "boilerplate"
        )
        .jsSettings(
          name := id + "-js",
          // Disables sbt-doctests for scala.js: https://github.com/tkawachi/sbt-doctest/issues/52
          doctestGenTests := Seq.empty,
          // Disables coverage in JS mode: https://github.com/scoverage/scalac-scoverage-plugin/issues/196
          coverageEnabled := false
        )
        .jvmSettings(name := id + "-jvm")

    /** Adds a `.laws` method for scala.js projects. */
    implicit class KantanJsOperations(val proj: CrossProject) extends AnyVal {
      def laws(name: String): CrossProject =
        proj
          .jvmSettings(setLaws(name + "-jvm"))
          .jsSettings(setLaws(name + "-js"))

    }

  }

}
