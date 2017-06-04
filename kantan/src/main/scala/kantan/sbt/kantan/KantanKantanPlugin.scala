/*
 * Copyright 2017 Nicolas Rinaudo
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

package kantan.sbt.kantan

import com.typesafe.sbt.SbtGit.git
import de.heikoseeberger.sbtheader.license.Apache2_0
import kantan.sbt.strict.StrictKantanPlugin
import kantan.sbt.KantanPlugin.autoImport.license
import kantan.sbt.PublishedPlugin.autoImport._
import sbt._
import sbt.Keys._

/** Plugin that sets kantan-specific values.
  *
  * This is really only meant for kantan projects. Don't use this unless you're me.
  */
object KantanKantanPlugin extends AutoPlugin {
  object autoImport {
    val kantanProject: SettingKey[String] = settingKey("Name of the kantan project")
  }
  import autoImport._

  override def trigger = allRequirements

  override def requires = StrictKantanPlugin

  override lazy val projectSettings = generalSettings ++ remoteSettings

  lazy val generalSettings: Seq[Setting[_]] = Seq(
    name                    := s"kantan.${kantanProject.value}",
    developerName           := Some("Nicolas Rinaudo"),
    developerId             := Some("nrinaudo"),
    developerUrl            := Some(url("https://nrinaudo.github.io")),
    organizationHomepage    := Some(url("https://nrinaudo.github.io")),
    organization            := "com.nrinaudo",
    crossScalaVersions      := Seq("2.10.6", "2.11.11", "2.12.2"),
    license                 := Some(Apache2_0("2017", "Nicolas Rinaudo")),
    licenses                := Seq("Apache-2.0" → url("https://www.apache.org/licenses/LICENSE-2.0.html"))
  )

  /** Remote identifiers, computed from [[autoImport.kantanProject]]. */
  lazy val remoteSettings: Seq[Setting[_]] = Seq(
    homepage       := Some(url(s"https://nrinaudo.github.io/kantan.${kantanProject.value}")),
    apiURL         := Some(url(s"https://nrinaudo.github.io/kantan.${kantanProject.value}/api/")),
    git.remoteRepo := s"git@github.com:nrinaudo/kantan.${kantanProject.value}.git",
    scmInfo        := Some(ScmInfo(
      url(s"https://github.com/nrinaudo/kantan.${kantanProject.value}"),
      s"scm:git:git@github.com:nrinaudo/kantan.${kantanProject.value}.git"
    ))
  )
}
