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
addSbtPlugin("com.nrinaudo" % "kantan.sbt" % "1.6.0")
```

This will, among other things:

* set sane `scalac` options
* add dependencies on common compiler plugins (kind-projector)
* enable [sbt-doctest](https://github.com/tkawachi/sbt-doctest) (defaulting to scalatest)
* enable [sbt-header](https://github.com/sbt/sbt-header)
* create a `checkStyle` task used to aggregate all style validation tasks (empty by default).
* add a `validate` command that will run `checkStyle`, run all tests, generate all docs, and generate coverage report.
* add sonatype repos to default resolvers

You can also manually enable the following plugins, which all enable automatic header generation:

* `PublishedPlugin`: configures the project for publication
* `UnpublishedPlugin`: prevents the project from being published
* `DocumentationPlugin`: prevents the project from being published and configure [tut](https://github.com/tpolecat/tut),
  [sbt-site](https://github.com/sbt/sbt-site), [sbt-unidoc](https://github.com/sbt/sbt-unidoc) and
  [sbt-ghpages](https://github.com/sbt/sbt-ghpages) to generate a companion website for your project.



## `kantan.sbt-strict`

Adding the strict module is done by adding the following to your `project/plugins.sbt` file:

```scala
addSbtPlugin("com.nrinaudo" % "kantan.sbt-strict" % "1.6.0")
```

This is very similar to the core module, but will be much more strict when it comes to error handling. Standard
[warts](http://www.wartremover.org) and warnings will cause builds to fail.



## `kantan.sbt-boilerplate`

If you use [sbt-boilerplate](https://github.com/sbt/sbt-boilerplate), consider using the kantan wrapper for it instead:

```scala
addSbtPlugin("com.nrinaudo" % "kantan.sbt-boilerplate" % "1.6.0")
```

The only difference, at the time of writing, is that headers will be generated for template files as well.

## `kantan.sbt-scalastyle`

If you want to use scalastyle, you can use the dedicated kantan module:

```scala
addSbtPlugin("com.nrinaudo" % "kantan.sbt-scalastyle" % "1.6.0")
```

This adds `scalastyle` to the `checkStyle` task, and lets you define a resource from which to read the scalastyle
configuration. Note that, in order to avoid breaking other tools' integration with scalastyle, the resource will
be copied to the default location and kept up to date.

## `kantan.sbt-scalafmt`

If you want to use scalafmt, you can use the dedicated kantan module:

```scala
addSbtPlugin("com.nrinaudo" % "kantan.sbt-scalafmt" % "1.6.0")
```

This adds `scalafmt` to the `checkStyle` task, and lets you define a resource from which to read the scalafmt
configuration. Note that, in order to avoid breaking other tools' integration with scalafmt, the resource will
be copied to the default location and kept up to date.
