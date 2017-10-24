addSbtPlugin("de.heikoseeberger" %  "sbt-header"            % "3.0.2")
addSbtPlugin("org.xerial.sbt"    %  "sbt-sonatype"          % "2.0")
addSbtPlugin("com.jsuereth"      %  "sbt-pgp"               % "1.1.0")
addSbtPlugin("org.scalastyle"    %% "scalastyle-sbt-plugin" % "1.0.0")
addSbtPlugin("com.lucidchart"    %  "sbt-scalafmt"          % "1.14")
addSbtPlugin("org.wartremover"   %  "sbt-wartremover"       % "2.2.1")

libraryDependencies += "org.scala-sbt" %% "scripted-plugin" % sbtVersion.value
