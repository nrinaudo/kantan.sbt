scalafmtVersion := "1.1.0"

releaseProcess := Seq[ReleaseStep](
  kantan.sbt.release.KantanRelease.runCheckStyle
)
