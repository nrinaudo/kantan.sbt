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

import com.typesafe.sbt.sbtghpages.GhpagesPlugin, GhpagesPlugin.autoImport._
import com.typesafe.sbt.site.SitePlugin, SitePlugin.autoImport.makeSite
import com.typesafe.sbt.site.SitePlugin.autoImport.siteSubdirName
import com.typesafe.sbt.site.preprocess.PreprocessPlugin
import com.typesafe.sbt.site.preprocess.PreprocessPlugin.autoImport._
import com.typesafe.sbt.site.util.SiteHelpers._
import mdoc.MdocPlugin, MdocPlugin.autoImport._
import sbt._, Keys._, ScopeFilter.ProjectFilter
import sbtunidoc.BaseUnidocPlugin.autoImport._
import sbtunidoc.ScalaUnidocPlugin
import sbtunidoc.ScalaUnidocPlugin.autoImport._

/** Plugin for documentation projects.
  *
  * Enabling this will set things up so that:
  *  - `makeSite` compiles all mdoc files, generates the unidoc API and builds a complete documentation site.
  *  - `ghpagesPushSite` generates the site and pushes it to the current repository's github pages.
  */
object DocumentationPlugin extends AutoPlugin {

  override def trigger = noTrigger

  override def requires = PreprocessPlugin && UnpublishedPlugin && ScalaUnidocPlugin && GhpagesPlugin && MdocPlugin

  object autoImport {

    /** This is mostly meant as an internal setting, initialised if `scmInfo` is set. But you can override it. */
    val docSourceUrl: SettingKey[Option[String]] = settingKey("scalac -doc-source-url parameter")

    def inProjectsIf(predicate: Boolean)(projects: ProjectReference*): ProjectFilter =
      if(predicate) inProjects(projects: _*)
      else inProjects()
    val mdocSite    = taskKey[Seq[(File, String)]]("create mdoc documentation in a way that lets sbt-site grab it")
    val mdocSiteOut = settingKey[String]("name of the directory in which sbt-site will store mdoc documentation")
  }

  import autoImport._

  override def projectSettings: Seq[Setting[_]] = scaladocSettings ++ mdocSettings ++ ghpagesSettings ++ siteSettings

  def siteSettings: Seq[Setting[_]] = Seq(
    includeFilter in SitePlugin.autoImport.makeSite :=
      "*.yml" | "*.md" | "*.html" | "*.css" | "*.png" | "*.jpg" | "*.gif" | "*.js" | "*.eot" | "*.svg" | "*.ttf" |
        "*.woff" | "*.woff2" | "*.otf",
    // Lets sbt-site know about unidoc.
    siteSubdirName in ScalaUnidoc := "api",
    addMappingsToSiteDir(mappings in (ScalaUnidoc, packageDoc), siteSubdirName in ScalaUnidoc),
    // Configures task dependencies: doc → makeSite → mdoc
    makeSite := makeSite.dependsOn(mdocSite).value,
    doc      := (doc in Compile).dependsOn(SitePlugin.autoImport.makeSite).value,
    // Use a "managed" source directory for preprocessing - we want all documentation to be preprocessed, and the only
    // way I found to achieve that is to have all md files to be copied / generated to the same directory, and *then*
    // preprocess that.
    sourceDirectory in Preprocess := resourceManaged.value / "main" / "site-preprocess"
  )

  def ghpagesSettings: Seq[Setting[_]] = Seq(
    // We want ghpages to run jekyll for us - this means our build has zero dependency on non-JVM tools.
    ghpagesNoJekyll := false,
    // Makes sure we run makeSite before ghpagesPushSite, as I've been known to push out-of-date doc.
    ghpagesPushSite := ghpagesPushSite.dependsOn(makeSite).value
  )

  def mdocSettings: Seq[Setting[_]] = Seq(
    mdocSite := {
      mdoc.toTask(" ").value
      val out = mdocOut.value
      for {
        (file, name) <- out ** AllPassFilter --- out pair Path.relativeTo(out)
      } yield file -> name
    },
    mdocExtraArguments += "--no-link-hygiene",
    mdocSiteOut        := "./",
    mdocIn             := (sourceDirectory in Compile).value / "mdoc",
    mdocVariables := Map(
      "VERSION" -> version.value
    ),
    addMappingsToSiteDir(mdocSite, mdocSiteOut)
  )

  def scaladocSettings: Seq[Setting[_]] =
    Seq(
      docSourceUrl := scmInfo.value.map(i => s"${i.browseUrl}/tree/master€{FILE_PATH}.scala"),
      scalacOptions in (ScalaUnidoc, unidoc) ++= Seq(
        "-sourcepath",
        baseDirectory.in(LocalRootProject).value.getAbsolutePath,
        "-groups"
      ) ++ docSourceUrl.value.map(v => Seq("-doc-source-url", v)).getOrElse(Seq.empty)
    )
}
