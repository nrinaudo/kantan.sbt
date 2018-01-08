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

package kantan.sbt.scalastyle

import kantan.sbt.KantanPlugin, KantanPlugin.autoImport._
import kantan.sbt.Resources.copyIfNeeded
import org.scalastyle.sbt.ScalastylePlugin
import org.scalastyle.sbt.ScalastylePlugin.autoImport._
import sbt._

/** Provides support for shared scalastyle configuration files. */
object KantanScalastylePlugin extends AutoPlugin {
  object autoImport {
    val scalastyleResource: SettingKey[Option[String]] = settingKey("resource that holds the scalastyle stylesheet")
    val copyScalastyleConfig: TaskKey[Unit]            = taskKey("Copies the scalastyle resource if necessary")
  }

  import autoImport._

  override def trigger = allRequirements

  override def requires = KantanPlugin && ScalastylePlugin

  override lazy val projectSettings = Seq(
    scalastyleResource   := None,
    copyScalastyleConfig := scalastyleResource.value.foreach(r ⇒ copyIfNeeded(r, scalastyleConfig.value))
  ) ++ inConfig(Compile)(rawScalastyleSettings) ++ inConfig(Test)(rawScalastyleSettings)

  private def rawScalastyleSettings = Seq(
    scalastyle := scalastyle.dependsOn(copyScalastyleConfig).evaluated,
    checkStyle := checkStyle.dependsOn(scalastyle.toTask("")).value
  )
}
