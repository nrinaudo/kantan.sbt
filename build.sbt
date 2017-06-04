import de.heikoseeberger.sbtheader.AutomateHeaderPlugin
import de.heikoseeberger.sbtheader.HeaderKey._
import de.heikoseeberger.sbtheader.license.Apache2_0

lazy val baseSettings: Seq[sbt.Def.Setting[_]] = {
  val license = Apache2_0("2017", "Nicolas Rinaudo")
  Seq(
    sbtPlugin    := true,
    organization := "com.nrinaudo",
    licenses := Seq("Apache-2.0" → url("https://www.apache.org/licenses/LICENSE-2.0.html")),
    pomExtra := <developers>
      <developer>
        <id>nrinaudo</id>
        <name>Nicolas Rinaudo</name>
        <url>http://nrinaudo.github.io</url>
      </developer>
    </developers>,
    homepage := Some(url(s"https://nrinaudo.github.io/kantan.sbt")),
    scmInfo := Some(ScmInfo(
      url(s"https://github.com/nrinaudo/kantan.sbt"),
      s"scm:git:git@github.com:nrinaudo/kantan.sbt.git"
    )),
    scalacOptions ++= Seq("-feature", "-language:existentials"),
    headers := Map(
      "scala" → license,
      "java"  → license
    )
  ) ++ testSettings
}

lazy val testSettings = scriptedSettings ++ Seq(
  scriptedLaunchOpts ++= Seq("-Xmx1024M", "-XX:MaxPermSize=256M", "-Dplugin.version=" + version.value)
)

lazy val root = Project(id = "kantan-sbt", base = file("."))
  .settings(moduleName := "root")
  .settings(
    publish         := (),
    publishLocal    := (),
    publishArtifact := false
  )
  .aggregate(core, strict, kantan)

lazy val core = project
  .settings(
    moduleName := "kantan.sbt",
    name       := "core"
  )
  .settings(baseSettings)
  .enablePlugins(AutomateHeaderPlugin)
  .settings(
    addSbtPlugin("de.heikoseeberger"   %  "sbt-header"            % Versions.sbtHeader),
    addSbtPlugin("org.xerial.sbt"      %  "sbt-sonatype"          % Versions.sbtSonatype),
    addSbtPlugin("com.jsuereth"        %  "sbt-pgp"               % Versions.sbtPgp),
    addSbtPlugin("org.tpolecat"        %  "tut-plugin"            % Versions.tut),
    addSbtPlugin("com.typesafe.sbt"    %  "sbt-site"              % Versions.sbtSite),
    addSbtPlugin("com.eed3si9n"        %  "sbt-unidoc"            % Versions.sbtUnidoc),
    addSbtPlugin("com.typesafe.sbt"    %  "sbt-ghpages"           % Versions.sbtGhPages),
    addSbtPlugin("org.scalastyle"      %% "scalastyle-sbt-plugin" % Versions.scalastyle),
    addSbtPlugin("org.scoverage"       %% "sbt-scoverage"         % Versions.scoverage),
    addSbtPlugin("com.github.tkawachi" %  "sbt-doctest"           % Versions.sbtDoctest)
  )

lazy val strict = project
  .settings(
    moduleName := "kantan.sbt-strict",
    name       := "strict"
  )
  .settings(baseSettings)
  .enablePlugins(AutomateHeaderPlugin)
  .settings(addSbtPlugin("org.wartremover" % "sbt-wartremover" % Versions.wartRemover))
  .dependsOn(core)

lazy val kantan = project
  .settings(
    moduleName := "kantan.sbt-kantan",
    name       := "kantan"
  )
  .settings(baseSettings)
  .enablePlugins(AutomateHeaderPlugin)
  .dependsOn(strict)


addCommandAlias("validate", ";clean;scalastyle;test:scalastyle;compile;scripted")
