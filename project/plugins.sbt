addSbtPlugin("com.eed3si9n"      % "sbt-unidoc"             % "0.4.2")
addSbtPlugin("com.github.gseitz" % "sbt-release"            % "1.0.11")
addSbtPlugin("com.jsuereth"      % "sbt-pgp"                % "1.1.2")
addSbtPlugin("org.scalameta"     % "sbt-scalafmt"           % "2.0.0")
addSbtPlugin("de.heikoseeberger" % "sbt-header"             % "5.2.0")
addSbtPlugin("com.typesafe.sbt"  % "sbt-ghpages"            % "0.6.3")
addSbtPlugin("com.typesafe.sbt"  % "sbt-site"               % "1.3.2")
addSbtPlugin("org.scalastyle"    %% "scalastyle-sbt-plugin" % "1.0.0")
addSbtPlugin("org.wartremover"   % "sbt-wartremover"        % "2.4.2")
addSbtPlugin("org.xerial.sbt"    % "sbt-sonatype"           % "2.3")

libraryDependencies += "org.scala-sbt" %% "scripted-plugin" % sbtVersion.value
