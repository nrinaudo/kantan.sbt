kantanProject in ThisBuild := "foo"
startYear                  := Some(2017)
scalafmtConfig             := file(".foobar.conf")

lazy val check = TaskKey[Unit]("check")

check := {
  if(!file(".foobar.conf").exists)
    sys.error("scalafmt configuration not found where expected")
  ()
}
