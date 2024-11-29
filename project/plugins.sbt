addSbtPlugin("com.github.sbt"    % "sbt-release"            % "1.4.0")
addSbtPlugin("com.github.sbt"    % "sbt-pgp"                % "2.3.0")
addSbtPlugin("org.scalameta"     % "sbt-scalafmt"           % "2.5.2")
addSbtPlugin("de.heikoseeberger" % "sbt-header"             % "5.10.0")
addSbtPlugin("org.jmotor.sbt"    % "sbt-dependency-updates" % "1.2.9")
// addSbtPlugin("com.github.sbt"    % "sbt-unidoc"          % "0.5.0")
// addSbtPlugin("com.github.sbt"    % "sbt-pgp"             % "2.1.2")
// addSbtPlugin("com.typesafe.sbt"  % "sbt-ghpages"         % "0.6.3")
// addSbtPlugin("com.typesafe.sbt"  % "sbt-site"            % "1.4.1")
// addSbtPlugin("org.xerial.sbt" % "sbt-sonatype"           % "3.11.2")

libraryDependencies += "org.scala-sbt" %% "scripted-plugin" % sbtVersion.value
