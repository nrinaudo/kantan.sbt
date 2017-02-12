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

package kantan.sbt

import com.typesafe.sbt.sbtghpages.GhpagesPlugin
import com.typesafe.sbt.site.SitePlugin
import com.typesafe.sbt.site.preprocess.PreprocessPlugin
import com.typesafe.sbt.site.util.SiteHelpers._
import sbt._
import sbt.Keys._
import sbt.ScopeFilter.ProjectFilter
import sbtunidoc.Plugin._
import sbtunidoc.Plugin.UnidocKeys._
import tut.Plugin._

/** Plugin for documentation projects.
  *
  * Enabling this will set things up so that:
  *  - `makeSite` compiles all tut files, generates the unidoc API and builds a complete documentation site.
  *  - `ghpagesPushSite` generates the site and pushes it to the current repository's github pages.
  */
object DocumentationPlugin extends AutoPlugin {
  // - Public settings -------------------------------------------------------------------------------------------------
  // -------------------------------------------------------------------------------------------------------------------
  object autoImport {
    val tutSiteDir: SettingKey[String]           = settingKey("Website tutorial directory")
    val apiSiteDir: SettingKey[String]           = settingKey("Unidoc API directory")
    val docSourceUrl: SettingKey[Option[String]] = settingKey("scalac -doc-source-url parameter")

    def inProjectsIf(predicate: Boolean)(projects: ProjectReference*): ProjectFilter =
      if(predicate) inProjects(projects:_*)
      else          inProjects()
  }
  import autoImport._

  override def projectSettings = unidocSettings ++ tutSettings ++ Seq(
    tutSiteDir   := "_tut",
    apiSiteDir   := "api",
    docSourceUrl := scmInfo.value.map(_.browseUrl + "/tree/master€{FILE_PATH}.scala"),
    scalacOptions in (ScalaUnidoc, unidoc) ++= {
      Seq("-sourcepath", baseDirectory.in(LocalRootProject).value.getAbsolutePath) ++
      docSourceUrl.value.map(v ⇒ Seq("-doc-source-url", v)).getOrElse(Seq.empty)
    },
    tutNameFilter := ((if(!BuildProperties.java8Supported) "^(?!java8)" else "") + ".*\\.(md|markdown)").r,
    tutScalacOptions ~= (_.filterNot(Set("-Ywarn-unused-import"))),
    GhpagesPlugin.autoImport.ghpagesNoJekyll := false,
    includeFilter in SitePlugin.autoImport.makeSite :=
    "*.yml" | "*.md" | "*.html" | "*.css" | "*.png" | "*.jpg" | "*.gif" | "*.js" | "*.eot" | "*.svg" | "*.ttf" |
    "*.woff" | "*.woff2" | "*.otf",
    addMappingsToSiteDir(tut, tutSiteDir),
    addMappingsToSiteDir(mappings in (ScalaUnidoc, packageDoc), apiSiteDir),
    // The doc task will also generate the documentation site.
    doc := (doc in Compile).dependsOn(SitePlugin.autoImport.makeSite).value
  )

  override def requires = PreprocessPlugin && UnpublishedPlugin && GhpagesPlugin

  override def trigger = noTrigger
}
