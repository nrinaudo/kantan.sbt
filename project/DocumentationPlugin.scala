import com.github.sbt.git.SbtGit.git
import com.github.sbt.sbtghpages.GhpagesPlugin, GhpagesPlugin.autoImport._
import com.typesafe.sbt.site.SitePlugin, SitePlugin.autoImport.makeSite
import com.typesafe.sbt.site.SitePlugin.autoImport.siteSubdirName
import com.typesafe.sbt.site.preprocess.PreprocessPlugin
import com.typesafe.sbt.site.preprocess.PreprocessPlugin.autoImport._
import com.typesafe.sbt.site.util.SiteHelpers._
import sbt._, Keys._
import sbt.plugins.JvmPlugin

object DocumentationPlugin extends AutoPlugin {
  override def trigger = noTrigger

  override def requires = JvmPlugin && PreprocessPlugin && GhpagesPlugin

  override lazy val projectSettings = Seq(
    GhpagesPlugin.autoImport.ghpagesNoJekyll := false,
    git.remoteRepo                           := s"git@github.com:nrinaudo/kantan.sbt.git",
    SitePlugin.autoImport.makeSite / includeFilter :=
      "*.yml" | "*.md" | "*.html" | "*.css" | "*.png" | "*.jpg" | "*.gif" | "*.js" | "*.eot" | "*.svg" | "*.ttf" |
        "*.woff" | "*.woff2" | "*.otf",
    ghpagesPushSite := ghpagesPushSite.dependsOn(makeSite).value,
    // The doc task will also generate the documentation site.
    doc := (Compile / doc).dependsOn(SitePlugin.autoImport.makeSite).value,
    // List of settings grabbed from https://github.com/scala/scala-parallel-collections/pull/14.
    makePom         := file(""),
    deliver         := file(""),
    deliverLocal    := file(""),
    publish         := {},
    publishLocal    := {},
    publishM2       := {},
    publishArtifact := false,
    publishTo       := Some(Resolver.file("devnull", file("/dev/null")))
  )
}
