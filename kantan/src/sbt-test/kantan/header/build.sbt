ThisBuild / kantanProject := "foo"
startYear                 := Some(2017)

lazy val check = TaskKey[Unit]("check")

check := {
  val actual   = IO.readLines((scalaSource in Compile).value / "Foo.scala")
  val expected = IO.readLines(file("expected.txt"))

  if(expected != actual)
    sys.error(s"Output doesn't match expected: \n${actual.mkString("\n")}")
  ()
}
