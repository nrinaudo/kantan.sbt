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

import de.heikoseeberger.sbtheader.AutomateHeaderPlugin
import sbt._
import sbt.Keys._
import scala.xml.transform.{RewriteRule, RuleTransformer}

/** Configures projects whose artifacts are meant for publication to maven.
  *
  * This is mostly meant as a "tag" for other plugins to piggyback. See, for example, `KantanKantanPlugin`.
  */
object PublishedPlugin extends AutoPlugin {
  override def requires = KantanPlugin && AutomateHeaderPlugin

  override def trigger = noTrigger
}