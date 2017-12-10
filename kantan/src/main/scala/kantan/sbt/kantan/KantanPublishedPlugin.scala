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

package kantan.sbt.kantan

import com.typesafe.sbt.pgp.PgpKeys.publishSigned
import kantan.sbt.PublishedPlugin
import kantan.sbt.release.{KantanRelease, KantanReleasePlugin}
import sbt._, Keys._
import sbtrelease.ReleasePlugin, ReleasePlugin.autoImport._, ReleaseTransformations._

/** Configures publication for kantan projects. */
object KantanPublishedPlugin extends AutoPlugin {
  override def trigger = allRequirements

  override def requires = KantanKantanPlugin && PublishedPlugin && KantanReleasePlugin

  /** Runs publishSigned. */
  lazy val runPublishSigned: State ⇒ State = { st: State ⇒
    val extracted = Project.extract(st)
    val ref       = extracted.get(thisProjectRef)
    extracted.runAggregated(publishSigned in Global in ref, st)
  }

  override lazy val projectSettings = Seq(
    releaseCrossBuild := true,
    releaseProcess := Seq[ReleaseStep](
      checkSnapshotDependencies,
      inquireVersions,
      runClean,
      KantanRelease.runCoverageOff,
      KantanRelease.runCheckStyle,
      runTest,
      setReleaseVersion,
      commitReleaseVersion,
      tagRelease,
      runPublishSigned,
      setNextVersion,
      commitNextVersion,
      releaseStepCommand("sonatypeReleaseAll"),
      KantanRelease.runPushSite,
      pushChanges
    ),
    publishTo := Some(
      if(isSnapshot.value)
        Opts.resolver.sonatypeSnapshots
      else
        Opts.resolver.sonatypeStaging
    )
  )
}
