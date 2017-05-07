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

scalacOptions ++= Seq("-feature", "-language:existentials")

headers := Map(
  "scala" → license,
  "java"  → license
)

enablePlugins(AutomateHeaderPlugin)

addSbtPlugin("de.heikoseeberger"   %  "sbt-header"            % Versions.sbtHeader)
addSbtPlugin("org.xerial.sbt"      %  "sbt-sonatype"          % Versions.sbtSonatype)
addSbtPlugin("com.jsuereth"        %  "sbt-pgp"               % Versions.sbtPgp)
addSbtPlugin("org.tpolecat"        %  "tut-plugin"            % Versions.tut)
addSbtPlugin("com.typesafe.sbt"    %  "sbt-site"              % Versions.sbtSite)
addSbtPlugin("com.eed3si9n"        %  "sbt-unidoc"            % Versions.sbtUnidoc)
addSbtPlugin("com.typesafe.sbt"    %  "sbt-ghpages"           % Versions.sbtGhPages)
addSbtPlugin("org.scalastyle"      %% "scalastyle-sbt-plugin" % Versions.scalastyle)
addSbtPlugin("org.scoverage"       %% "sbt-scoverage"         % Versions.scoverage)
addSbtPlugin("com.github.tkawachi" %  "sbt-doctest"           % Versions.sbtDoctest)
addSbtPlugin("org.wartremover"     % "sbt-wartremover"        % Versions.wartRemover)

addCommandAlias("validate", ";clean;scalastyle;test:scalastyle;compile")
