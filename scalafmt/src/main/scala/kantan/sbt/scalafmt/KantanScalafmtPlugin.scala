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

package kantan.sbt.scalafmt

import kantan.sbt.KantanPlugin, KantanPlugin.autoImport._
import kantan.sbt.Resources._
import org.scalafmt.sbt.ScalafmtPlugin, ScalafmtPlugin.autoImport._
import sbt._

/** Provides support for shared scalafmt configuration files. */
object KantanScalafmtPlugin extends AutoPlugin {
  object autoImport {
    val scalafmtResource: SettingKey[Option[String]] = settingKey("resource that holds the scalafmt configuration")
    val scalafmtAll: TaskKey[Unit]                   = taskKey("Runs scalafmt on all sources")
    val copyScalafmtConfig: TaskKey[Unit]            = taskKey("Copies the scalafmt resource if necessary")
  }

  import autoImport._

  val defaultConf = file(".scalafmt.conf")

  override def trigger = allRequirements

  override def requires = KantanPlugin && ScalafmtPlugin

  override lazy val projectSettings = rawScalafmtSettings(Compile, Test) ++ checkStyleSettings ++ Seq(
    scalafmtResource := None,
    scalafmtAll      := (scalafmt in Compile).dependsOn(scalafmt in Test).dependsOn(scalafmtSbt in Compile).value,
    // scalafmtConfig is annoying: the default implementation checks whether .scalafmt.conf exists and, if it doesn't,
    // takes a value of None. This is problematic for our use case: copyScalafmtConfig depends on scalafmtConfig,
    // which means things happen in the following order:
    // - copyScalafmtConfig gets the value of scalafmtConfig
    // - since .scalafmt.conf does not exist, this is None
    // - we copy the appropriate resource to .scalafmt.conf
    // - scalafmt, seeing the None value, uses default settings and ignores our configuration
    //
    // The point of the code bellow is to make scalafmtConfig aware of scalafmtResource.
    scalafmtConfig := {
      scalafmtConfig.value.orElse(scalafmtResource.value.map(_ => defaultConf))
    },
    copyScalafmtConfig := scalafmtResource.value.foreach(
      r => copyIfNeeded(r, scalafmtConfig.value.getOrElse(defaultConf))
    )
  )

  // Makes sure checkStyle depends on the right scalafmt commands depending on the context.
  private def checkStyleSettings: Seq[Setting[_]] =
    Seq(
      (checkStyle in Compile) := (checkStyle in Compile)
        .dependsOn(scalafmtCheck in Compile, scalafmtSbtCheck in Compile)
        .value,
      (checkStyle in Test) := (checkStyle in Test).dependsOn(scalafmtCheck in Test).value
    )

  // Makes sure all relevant scalafmt tasks depend on copyScalafmtConfig
  private def rawScalafmtSettings(configs: Configuration*): Seq[Setting[_]] =
    configs.flatMap { config =>
      inConfig(config)(
        Seq(
          scalafmtCheck    := scalafmtCheck.dependsOn(copyScalafmtConfig).value,
          scalafmt         := scalafmt.dependsOn(copyScalafmtConfig).value,
          scalafmtSbtCheck := scalafmtSbtCheck.dependsOn(copyScalafmtConfig).value,
          scalafmtSbt      := scalafmtSbt.dependsOn(copyScalafmtConfig).value
        )
      )
    }
}
