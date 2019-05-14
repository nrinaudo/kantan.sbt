scalaVersion := "2.11.12"

lazy val check = TaskKey[Unit]("check")

check := {
  val options = javacOptions.value.mkString(" ")

  if(!(options.contains("-source 1.6") && options.contains("-target 1.6")))
    sys.error(s"Expected source and target to be 1.6, but javacOptions are ${options}")
}
