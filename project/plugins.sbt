addSbtPlugin("ch.epfl.scala"     % "sbt-scalafix"           % "0.13.0")
addSbtPlugin("com.github.sbt"    % "sbt-unidoc"             % "0.5.0")
addSbtPlugin("com.github.sbt"    % "sbt-release"            % "1.4.0")
addSbtPlugin("com.github.sbt"    % "sbt-pgp"                % "2.3.1")
addSbtPlugin("de.heikoseeberger" % "sbt-header"             % "5.10.0")
addSbtPlugin("com.github.sbt"    % "sbt-ghpages"            % "0.8.0")
addSbtPlugin("com.github.sbt"    % "sbt-site"               % "1.7.0")
addSbtPlugin("org.wartremover"   % "sbt-wartremover"        % "3.2.5")
addSbtPlugin("org.xerial.sbt"    % "sbt-sonatype"           % "3.12.2")

addSbtPlugin(
  ("org.scalameta" % "sbt-scalafmt" % "2.5.2")
    .excludeAll(ExclusionRule("com.github.plokhotnyuk.jsoniter-scala"))
)
