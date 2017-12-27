addSbtPlugin("com.github.gseitz" % "sbt-release"            % "1.0.7")
addSbtPlugin("com.jsuereth"      %  "sbt-pgp"               % "1.1.0")
addSbtPlugin("com.lucidchart"    %  "sbt-scalafmt"          % "1.15")
addSbtPlugin("de.heikoseeberger" %  "sbt-header"            % "3.0.2")
addSbtPlugin("org.scalastyle"    %% "scalastyle-sbt-plugin" % "1.0.0")
addSbtPlugin("org.wartremover"   %  "sbt-wartremover"       % "2.2.1")
addSbtPlugin("org.xerial.sbt"    %  "sbt-sonatype"          % "2.0")

libraryDependencies += "org.scala-sbt" %% "scripted-plugin" % sbtVersion.value
