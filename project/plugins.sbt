addSbtPlugin("com.github.sbt"    % "sbt-unidoc"             % "0.5.0")
addSbtPlugin("com.github.sbt"    % "sbt-release"            % "1.1.0")
addSbtPlugin("com.github.sbt"    % "sbt-pgp"                % "2.1.2")
addSbtPlugin("org.scalameta"     % "sbt-scalafmt"           % "2.4.6")
addSbtPlugin("de.heikoseeberger" % "sbt-header"             % "5.6.5")
addSbtPlugin("com.typesafe.sbt"  % "sbt-ghpages"            % "0.6.3")
addSbtPlugin("com.typesafe.sbt"  % "sbt-site"               % "1.4.1")
addSbtPlugin("org.scalastyle"    %% "scalastyle-sbt-plugin" % "1.0.0")
addSbtPlugin("org.wartremover"   % "sbt-wartremover"        % "2.4.17")
addSbtPlugin("org.xerial.sbt"    % "sbt-sonatype"           % "3.9.16")

libraryDependencies += "org.scala-sbt" %% "scripted-plugin" % sbtVersion.value
