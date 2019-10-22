scalaVersion := "2.12.10"

lazy val check = TaskKey[Unit]("check")

check := {
  val options = javacOptions.value.mkString(" ")

  if(!(options.contains("-source 1.8") && options.contains("-target 1.8")))
    sys.error(s"Expected source and target to be 1.8, but javacOptions are ${options}")
}
