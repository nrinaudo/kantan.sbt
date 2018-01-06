releaseProcess := Seq[ReleaseStep](
  kantan.sbt.release.KantanRelease.runCheckStyle
)
