# kantan.sbt

[![Build Status](https://travis-ci.org/nrinaudo/kantan.sbt.svg?branch=master)](https://travis-ci.org/nrinaudo/kantan.sbt)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.nrinaudo/kantan.sbt/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.nrinaudo/kantan.sbt)

## Overview and warning
Collection of SBT plugins used to aggregate all common settings and dependencies for kantan projects.

This is strictly a support project and should never be imported for anything but one of the kantan libraries: it'll set
default settings that are only valid if you're, well, me.

Enabling `kantan.sbt` is done by adding the following line to your `project/plugins.sbt` file:

```scala
addSbtPlugin("com.nrinaudo" % "kantan.sbt" % "1.2.4")
```

Once that's done, the following plugins will be available:

* `KantanPlugin`: automatically imported, sets sane default values for things like `scalac` options.
* `UnpublishedPlugin`: needs to be enabled, configures projects whose artifacts are not meant for publication.
* `PublishedPlugin`: needs to be enabled, configures projects whose artifacts are meant for publication to maven central.
* `DocumentationPlugin`: needs to be enabled, configures projects whose output is a documentation site.
