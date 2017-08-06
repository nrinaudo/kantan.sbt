addSbtPlugin("de.heikoseeberger" %  "sbt-header"            % "2.0.0")
addSbtPlugin("org.xerial.sbt"    %  "sbt-sonatype"          % "1.1")
addSbtPlugin("com.jsuereth"      %  "sbt-pgp"               % "1.0.1")
addSbtPlugin("org.scalastyle"    %% "scalastyle-sbt-plugin" % "0.8.0")


libraryDependencies += "org.scala-sbt" % "scripted-plugin" % sbtVersion.value
