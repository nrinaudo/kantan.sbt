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

import de.heikoseeberger.sbtheader.AutomateHeaderPlugin
import sbt._
import sbt.Keys._
import scala.xml.transform.{RewriteRule, RuleTransformer}

/** Configures projects whose artifacts are meant for publication to maven central.
  *
  * This mostly has to do with configuring POM files correctly.
  */
object PublishedPlugin extends AutoPlugin {
  override def projectSettings = Seq(
    licenses := Seq("Apache-2.0" → url("https://www.apache.org/licenses/LICENSE-2.0.html")),
    pomExtra := <developers>
      <developer>
        <id>nrinaudo</id>
        <name>Nicolas Rinaudo</name>
        <url>http://nrinaudo.github.io</url>
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
