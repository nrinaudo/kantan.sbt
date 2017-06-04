/*
 * Copyright 2016 com.nrinaudo
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

import de.heikoseeberger.sbtheader.AutomateHeaderPlugin
import sbt._
import sbt.Keys._
import scala.xml.transform.{RewriteRule, RuleTransformer}

/** Configures projects whose artifacts are meant for publication to maven.
  *
  * This mostly has to do with configuring POM files correctly.
  */
object PublishedPlugin extends AutoPlugin {
  object autoImport {
    val developerId: SettingKey[Option[String]] = settingKey("Identifier of the developer publishing the artifacts")
    val developerName: SettingKey[Option[String]] = settingKey("Name of the developer publishing the artifacts")
    val developerUrl: SettingKey[Option[URL]] = settingKey("URL of the developer's website")
  }

  import autoImport._

  override def projectSettings = Seq(
    developerId    := None,
    developerName  := None,
    developerUrl   := None,
    pomExtra       :=
      <developers>
        <developer>
          { developerId.value. map(d ⇒ <id>{d}</id>).getOrElse("") }
          { developerName.value.map(n ⇒ <name>{n}</name>).getOrElse("") }
          { developerUrl.value.map(u ⇒ <url>{u}</url>).getOrElse("") }
        </developer>
      </developers>,
    pomPostProcess := { (node: xml.Node) ⇒
      new RuleTransformer(
        new RewriteRule {
          override def transform(node: xml.Node): Seq[xml.Node] = node match {
            case e: xml.Elem
              if e.label == "dependency" && e.child.exists(child ⇒ child.label == "groupId" &&
                                                                   child.text == "org.scoverage") ⇒ Nil
            case _ ⇒ Seq(node)
          }
        }).transform(node).head
    }
  )

  override def requires = KantanPlugin && AutomateHeaderPlugin

  override def trigger = noTrigger
}
