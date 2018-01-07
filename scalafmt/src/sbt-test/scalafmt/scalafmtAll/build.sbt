lazy val check = TaskKey[Unit]("check")

check := {
  def validate(actualFile: File, expectedFile: File): Unit = {
    val actual   = IO.readLines(actualFile)
    val expected = IO.readLines(expectedFile)

    if(expected != actual)
      sys.error(s"Output '${actualFile.getName}' doesn't match '${expectedFile.getName}'")
  }

  validate(
    (scalaSource in Compile).value / "Foo.scala",
    file("expected/foo.txt")
  )

  validate(
    (scalaSource in Test).value / "Bar.scala",
    file("expected/bar.txt")
  )

  validate(
    file("project/plugins.sbt"),
    file("expected/plugins.txt")
  )

  validate(
    file("project/Versions.scala"),
    file("expected/Versions.txt")
  )

  ()
}
