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

package kantan.sbt.boilerplate

import de.heikoseeberger.sbtheader.FileType
import de.heikoseeberger.sbtheader.HeaderPlugin
import de.heikoseeberger.sbtheader.HeaderPlugin.autoImport._
import sbt._, Keys._
import spray.boilerplate.BoilerplatePlugin

/** Add support for headers in sbt-boilerplate. */
object KantanBoilerplatePlugin extends AutoPlugin {
  override def trigger = allRequirements

  override def requires = HeaderPlugin && BoilerplatePlugin

  override lazy val projectSettings = addBoilerplate(Compile, Test)

  private def addBoilerplate(confs: Configuration*): List[Setting[_]] =
    confs.foldLeft(List.empty[Setting[_]]) { (acc, conf) ⇒
      acc ++ Seq(
        unmanagedSources in (conf, headerCreate) ++= (((sourceDirectory in conf).value / "boilerplate") **
          "*.template").get,
        headerMappings += (FileType("template") → HeaderCommentStyle.CStyleBlockComment)
      )
    }
}
