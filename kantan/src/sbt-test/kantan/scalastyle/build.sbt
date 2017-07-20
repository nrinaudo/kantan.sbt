import sbt.IO

kantanProject in ThisBuild := "foo"
startYear                  := Some(2017)

lazy val checkExpected = TaskKey[Unit]("checkExpected")
lazy val checkDoesntExist = TaskKey[Unit]("checkDoesntExist")
lazy val overwrite = TaskKey[Unit]("overwrite")

def confFile: Option[File] = {
  val conf = file("scalastyle-config.xml")

  if(conf.exists) Some(conf)
  else            None
}

val charset = java.nio.charset.Charset.forName("utf-8")

def scalastyleText: Option[String] =
  confFile.map(c ⇒ IO.read(c, charset))

checkExpected := {
  val content = IO.readStream(getClass.getResourceAsStream("/kantan/sbt/scalastyle-config.xml"), charset)
  if(!scalastyleText.forall(_ == content))
    sys.error(s"Failed to find scalastyle-config.xml, or found it with unexpected content")
  ()
}

checkDoesntExist := {
  if(confFile.isDefined) sys.error(s"Expected not to find scalastyle-config.xml but did")
  ()
}

overwrite := {
  sbt.IO.write(file("scalastyle-config.xml"), "foobar", charset)
}
