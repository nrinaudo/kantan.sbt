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

import com.lucidchart.sbt.scalafmt.{ScalafmtCorePlugin, ScalafmtSbtPlugin}
import com.lucidchart.sbt.scalafmt.ScalafmtCorePlugin.autoImport._
import com.lucidchart.sbt.scalafmt.ScalafmtSbtPlugin.autoImport._
import kantan.sbt.{BuildProperties, KantanPlugin}
import kantan.sbt.KantanPlugin.autoImport._
import kantan.sbt.Resources._
import sbt._
import sbt.Keys._

/** Provides support for shared scalafmt configuration files. */
object KantanScalafmtPlugin extends AutoPlugin {
  object autoImport {
    val scalafmtResource: SettingKey[Option[String]] = settingKey("resource that holds the scalafmt configuration")
    val copyScalafmtConfig: TaskKey[Unit] = taskKey("Copies the scalafmt resource if necessary")
  }

  import autoImport._

  override def trigger = allRequirements

  override def requires = KantanPlugin && ScalafmtCorePlugin && ScalafmtSbtPlugin

  override lazy val projectSettings = Seq(
    scalafmtResource      := None,
    copyScalafmtConfig    := scalafmtResource.value.foreach(r ⇒ copyIfNeeded(r, file(".scalafmt.conf")))
  ) ++ rawScalafmtSettings(Compile, Test, Sbt) ++ checkStyleSettings

  // Makes sure checkStyle depends on the right scalafmt commands depending on the context.
  private def checkStyleSettings: Seq[Setting[_]] =
    if(BuildProperties.java8Supported) Seq(
    (checkStyle in Compile) := (checkStyle in Compile).dependsOn(test in (Compile, scalafmt),
      test in (Sbt, scalafmt)).value,
    (checkStyle in Test) := (checkStyle in Test).dependsOn(test in (Test, scalafmt)).value)
    else Seq.empty

  // Makes sure all relevant scalafmt tasks depend on copyScalafmtConfig
  private def rawScalafmtSettings(configs: Configuration*): Seq[Setting[_]] =
    configs.flatMap { config ⇒ inConfig(config)(Seq(
      test in scalafmt := (test in scalafmt).dependsOn(copyScalafmtConfig).value,
      scalafmt         := scalafmt.dependsOn(copyScalafmtConfig).value
    ))
    }
}
