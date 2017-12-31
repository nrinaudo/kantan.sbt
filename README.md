# kantan.sbt

[![Build Status](https://travis-ci.org/nrinaudo/kantan.sbt.svg?branch=master)](https://travis-ci.org/nrinaudo/kantan.sbt)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.nrinaudo/kantan.sbt/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.nrinaudo/kantan.sbt)

## Overview and warning
Collection of SBT plugins and settings used by all kantan.projects.

This _can_ be used by other projects (only the `kantan` module is strictly kantan specific), but bear in mind that I wrote this with
my own needs in mind, and it might not yet be generic enough to plug in seemlessly into other buildfiles.

## `kantan.sbt`

Adding the core module is done by adding the following to your `project/plugins.sbt` file:

```scala
addSbtPlugin("com.nrinaudo" % "kantan.sbt" % "2.0.0")
```

This will, among other things:

* set sane `scalac` options
* add dependencies on common compiler plugins (kind-projector)
* enable [sbt-doctest](https://github.com/tkawachi/sbt-doctest) (defaulting to scalatest)
* enable [sbt-header](https://github.com/sbt/sbt-header)
* create a `checkStyle` task used to aggregate all style validation tasks.
* enable [neo-sbt-scalafmt](https://github.com/lucidsoftware/neo-sbt-scalafmt)
* enable [scalastyle](https://github.com/scalastyle/scalastyle-sbt-plugin)
* add a `validate` command that will run `checkStyle`, run all tests, generate all docs, and generate coverage report.

You can also manually enable the following plugins:

* `PublishedPlugin`: configures the project for publication
* `UnpublishedPlugin`: prevents the project from being published
* `DocumentationPlugin`: prevents the project from being published and configure [tut](https://github.com/tpolecat/tut),
  [sbt-site](https://github.com/sbt/sbt-site), [sbt-unidoc](https://github.com/sbt/sbt-unidoc) and
  [sbt-ghpages](https://github.com/sbt/sbt-ghpages) to generate a companion website for your project.
* `StrictPlugin`: makes all warning fatal and enables [wartremover](https://github.com/wartremover/wartremover) with
  safe default warts.
