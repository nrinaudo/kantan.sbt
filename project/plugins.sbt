addSbtPlugin("com.eed3si9n"      % "sbt-unidoc"             % "0.4.1")
addSbtPlugin("com.github.gseitz" % "sbt-release"            % "1.0.8")
addSbtPlugin("com.jsuereth"      % "sbt-pgp"                % "1.1.0")
addSbtPlugin("com.geirsson"      % "sbt-scalafmt"           % "1.4.0")
addSbtPlugin("de.heikoseeberger" % "sbt-header"             % "5.0.0")
addSbtPlugin("com.typesafe.sbt"  % "sbt-ghpages"            % "0.6.2")
addSbtPlugin("com.typesafe.sbt"  % "sbt-site"               % "1.3.2")
addSbtPlugin("org.scalastyle"    %% "scalastyle-sbt-plugin" % "1.0.0")
addSbtPlugin("org.wartremover"   % "sbt-wartremover"        % "2.2.1")
addSbtPlugin("org.xerial.sbt"    % "sbt-sonatype"           % "2.0")

libraryDependencies += "org.scala-sbt" %% "scripted-plugin" % sbtVersion.value
