enablePlugins(PublishedPlugin)

name                 := "foo"
organization         := "com.baz"
organizationHomepage := Some(url("https://baz.url"))
version              := "1.2.3"


version := "1.0.0"

lazy val check = TaskKey[Unit]("check")

check := {
  import kantan.xpath._
  import kantan.xpath.implicits._

  val pom = makePomConfiguration.value.file.asUnsafeNode

  def assertPom[A: NodeDecoder](path: String, expected: A): Unit =  {
    val actual = pom.unsafeEvalXPath(Query.unsafeCompile[A](path))
    assert(actual == expected, s"$path: Expected '$expected' but found '$actual'")
  }

  // Basic description
  assertPom("/project/name",        "foo")
  assertPom("/project/description", "foo")
  assertPom("/project/url",         Option.empty[String])

  // License info
  assertPom("/project/licenses", Option.empty[String])

  // Organization info
  assertPom("/project/organization/name", "com.baz")
  assertPom("/project/organization/url",  "https://baz.url")

  // SCM info
  assertPom("/project/scm", Option.empty[String])

  // Documentation
  assertPom("/project/properties", Option.empty[String])
}
