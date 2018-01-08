kantanProject in ThisBuild := "foo"
startYear                  := Some(2017)
scalastyleConfig           := file("scalastyle.xml")

lazy val check = TaskKey[Unit]("check")

check := {
  if(!file("scalastyle.xml").exists)
    sys.error("scalastyle configuration not found where expected")
  ()
}
