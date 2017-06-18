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

package kantan.sbt.strict

import kantan.sbt.KantanPlugin
import sbt._
import sbt.Keys._
import wartremover.{Wart, WartRemover, Warts}

/** Makes compilation much more strict.
  *
  * This will make warnings fatal, as well as turn on various Scala linters (such as WartRemover).
  */
object StrictKantanPlugin extends AutoPlugin {
  override def trigger = allRequirements

  override def requires = KantanPlugin && WartRemover

  override lazy val projectSettings = wartRemoverSettings ++ scalacSettings

  /** All warnings are fatal in `Compile`.
    *
    * I'd love to make warnings fatal in `Test` as well, but the problem is that some tests actually need to do some
    * dodgy things to see what happens.
    */
  def scalacSettings: Seq[Setting[_]] = Seq(
    scalacOptions in (Compile, compile) += "-Xfatal-warnings"
  )

  /** WartRemover settings, disabled for 2.10 because of weird compatibility issues that I can't bother to get into.
    * 2.10 support is going to be dropped sooner rather than later anyway.
    */
  def wartRemoverSettings: Seq[Setting[_]] = {
    List(Compile, Test).flatMap { c ⇒ inConfig(c)(WartRemover.autoImport.wartremoverErrors in (Compile, compile) ++= {
      if(scalaVersion.value.startsWith("2.10")) Seq.empty
      // Removes Warts that have too many false positives (and are mostly covered by other tools as well anyway).
      else Warts.allBut(Wart.NonUnitStatements,
        Wart.Equals, Wart.Overloading, Wart.ImplicitParameter, Wart.Nothing, Wart.ImplicitConversion, Wart.Any,
        Wart.ToString, Wart.PublicInference, Wart.Recursion)
    })}
  }
}
