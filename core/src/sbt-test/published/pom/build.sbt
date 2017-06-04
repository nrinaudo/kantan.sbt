enablePlugins(PublishedPlugin)

name          := "foo"
organization  := "com.baz"
version       := "1.2.3"
developerId   := Some("com.baz.id")
developerName := Some("Baz Name")
developerUrl  := Some(url("https://baz.url"))


version := "1.0.0"

lazy val check = TaskKey[Unit]("check")

check := {
  import kantan.xpath._
  import kantan.xpath.implicits._

  val pom = makePomConfiguration.value.file.asUnsafeNode

  def assertPom[A: NodeDecoder](path: String, expected: A): Unit =
    assert(pom.unsafeEvalXPath(Query.unsafeCompile[A](path)) == expected)

  // Basic description
  assertPom("/project/name",        "foo")
  assertPom("/project/description", "foo")
  assertPom("/project/url",         Option.empty[String])

  // License info
  assertPom("/project/licenses", Option.empty[String])

  // Organization info
  assertPom("/project/organization/name", "com.baz")
  assertPom("/project/organization/url",  Option.empty[String])

  // SCM info
  assertPom("/project/scm", Option.empty[String])

  // Developer info
  assertPom("/project/developers/developer/id",   "com.baz.id")
  assertPom("/project/developers/developer/name", "Baz Name")
  assertPom("/project/developers/developer/url",  "https://baz.url")

  // Documentation
  assertPom("/project/properties", Option.empty[String])
}
