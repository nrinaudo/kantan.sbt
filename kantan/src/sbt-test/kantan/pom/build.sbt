kantanProject in ThisBuild := "foo"

enablePlugins(PublishedPlugin)

version := "1.0.0"

lazy val check = TaskKey[Unit]("check")

check := {
  import kantan.xpath._
  import kantan.xpath.implicits._

  val pom = makePomConfiguration.value.file.get.asUnsafeNode

  def assertPom[A: NodeDecoder](path: String, expected: A): Unit =  {
    val actual = pom.unsafeEvalXPath(Query.unsafeCompile[A](path))
    assert(actual == expected, s"$path: Expected '$expected' but found '$actual'")
  }

  // Basic description
  assertPom("/project/name",        "kantan.foo")
  assertPom("/project/description", "kantan.foo")
  assertPom("/project/url",         "https://nrinaudo.github.io/kantan.foo")
  assertPom("/project/groupId",     "com.nrinaudo")

  // License info
  assertPom("/project/licenses/license/name", "Apache-2.0")
  assertPom("/project/licenses/license/url",  "https://www.apache.org/licenses/LICENSE-2.0.html")

  // Organization info
  assertPom("/project/organization/name", "Nicolas Rinaudo")
  assertPom("/project/organization/url",  "https://nrinaudo.github.io")

  // SCM info
  assertPom("/project/scm/url",        "https://github.com/nrinaudo/kantan.foo")
  assertPom("/project/scm/connection", "scm:git:git@github.com:nrinaudo/kantan.foo.git")

  // Developer info
  assertPom("/project/developers/developer/id",   "nrinaudo")
  assertPom("/project/developers/developer/name", "Nicolas Rinaudo")
  assertPom("/project/developers/developer/url",  "https://twitter.com/nicolasrinaudo")

  // Documentation
  assertPom("/project/properties/info.apiURL",  "https://nrinaudo.github.io/kantan.foo/api/")
}
