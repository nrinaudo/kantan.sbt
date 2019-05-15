import sbt.IO

kantanProject in ThisBuild := "foo"
startYear                  := Some(2017)

lazy val checkExpected    = TaskKey[Unit]("checkExpected")
lazy val checkDoesntExist = TaskKey[Unit]("checkDoesntExist")
lazy val overwrite        = TaskKey[Unit]("overwrite")

def confFile: Option[File] = {
  val conf = file(".scalafmt.conf")

  if(conf.exists) Some(conf)
  else None
}

val charset = java.nio.charset.Charset.forName("utf-8")

def scalafmtText: Option[String] =
  confFile.map(c => IO.read(c, charset))

checkExpected := {
  val content =
    IO.readStream(getClass.getResourceAsStream("/kantan/sbt/scalafmt.conf"), charset)

  scalafmtText match {
    case Some(cnt) if cnt != content =>
      sys.error(s"found .scalafmt.conf with unexpected content")
    case None => sys.error(s"failed to find .scalafmt.conf")
    case _    => ()
  }
}

checkDoesntExist := {
  if(confFile.isDefined)
    sys.error(s"Expected not to find .scalafmt.conf but did")
  ()
}

overwrite := {
  sbt.IO.write(file(".scalafmt.conf"), "foobar", charset)
}
