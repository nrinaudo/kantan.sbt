import de.heikoseeberger.sbtheader.AutomateHeaderPlugin
import de.heikoseeberger.sbtheader.HeaderKey._
import de.heikoseeberger.sbtheader.license.Apache2_0

sbtPlugin    := true
organization := "com.nrinaudo"
name         := "kantan.sbt"
moduleName   := "kantan.sbt"

licenses := Seq("Apache-2.0" → url("https://www.apache.org/licenses/LICENSE-2.0.html"))
pomExtra := <developers>
  <developer>
    <id>nrinaudo</id>
    <name>Nicolas Rinaudo</name>
    <url>http://nrinaudo.github.io</url>
  </developer>
</developers>
homepage := Some(url(s"https://nrinaudo.github.io/kantan.sbt"))

scmInfo := Some(ScmInfo(
  url(s"https://github.com/nrinaudo/kantan.sbt"),
  s"scm:git:git@github.com:nrinaudo/kantan.sbt.git"
))

val license = Apache2_0("2017", "Nicolas Rinaudo")

headers := Map(
  "scala" → license,
  "java"  → license
)

enablePlugins(AutomateHeaderPlugin)

addSbtPlugin("de.heikoseeberger"   %  "sbt-header"            % "1.6.0")
addSbtPlugin("org.xerial.sbt"      %  "sbt-sonatype"          % "1.1")
addSbtPlugin("com.jsuereth"        %  "sbt-pgp"               % "1.0.0")
addSbtPlugin("org.tpolecat"        %  "tut-plugin"            % "0.4.8")
addSbtPlugin("com.typesafe.sbt"    %  "sbt-site"              % "1.1.0")
addSbtPlugin("com.eed3si9n"        %  "sbt-unidoc"            % "0.3.3")
addSbtPlugin("com.typesafe.sbt"    %  "sbt-ghpages"           % "0.6.0")
addSbtPlugin("de.heikoseeberger"   %  "sbt-header"            % "1.6.0")
addSbtPlugin("org.scalastyle"      %% "scalastyle-sbt-plugin" % "0.8.0")
addSbtPlugin("org.scoverage"       %% "sbt-scoverage"         % "1.5.0")
addSbtPlugin("com.github.tkawachi" %  "sbt-doctest"           % "0.5.0")

addCommandAlias("validate", ";clean;scalastyle;test:scalastyle;compile")
