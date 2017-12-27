object Versions {
  val boilerplate: String = "0.6.1"
  val sbtDoctest: String  = "0.7.0"
  val sbtGhPages: String  = "0.6.2"
  // This is not the latest version: 4.0.0 is out, but it suffers from a regression in the way headerCheck behaves
  // that makes it unusable for our purposes (read: untestable).
  // See https://gitter.im/sbt/sbt-header?at=5a2d9f160163b028101da746
  val sbtHeader: String   = "3.0.2"
  val sbtPgp: String      = "1.1.0"
  val sbtRelease: String  = "1.0.7"
  val sbtScalafmt: String = "1.15"
  val sbtSite: String     = "1.3.1"
  val sbtSonatype: String = "2.0"
  val sbtUnidoc: String   = "0.4.1"
  val scalafmt: String    = "1.3.0"
  val scalastyle: String  = "1.0.0"
  val scoverage: String   = "1.5.1"
  val tut: String         = "0.6.2"
  val wartRemover: String = "2.2.1"
}
