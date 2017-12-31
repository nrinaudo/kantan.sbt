releaseProcess := Seq[ReleaseStep](
  kantan.sbt.KantanRelease.runCoverageOff
)

lazy val check = TaskKey[Unit]("check")

check := {
  if(coverageEnabled.value)
    sys.error(s"Expected coverage to be disabled but isn't.")
  ()
}
