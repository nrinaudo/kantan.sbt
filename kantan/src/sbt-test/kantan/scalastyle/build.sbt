kantanProject in ThisBuild := "foo"
startYear                  := Some(2017)

lazy val checkExists = TaskKey[Unit]("checkExists")
lazy val checkDoesntExist = TaskKey[Unit]("checkDoesntExist")

checkExists := {
  if(!file("scalastyle-config.xml").exists())
    sys.error(s"Expected to find scalastyle-config.xml but did not")
  ()
}

checkDoesntExist := {
  if(file("scalastyle-config.xml").exists())
    sys.error(s"Expected not to find scalastyle-config.xml but did")
  ()
}
