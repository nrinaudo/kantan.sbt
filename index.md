---
layout: index
---

[![Build Status](https://travis-ci.org/nrinaudo/kantan.sbt.svg?branch=master)](https://travis-ci.org/nrinaudo/kantan.sbt)
[![Latest version](https://index.scala-lang.org/nrinaudo/kantan.sbt/kantan.sbt/latest.svg)](https://index.scala-lang.org/nrinaudo/kantan.sbt)
[![Join the chat at https://gitter.im/nrinaudo/kantan.sbt](https://img.shields.io/badge/gitter-join%20chat-52c435.svg)](https://gitter.im/nrinaudo/kantan.sbt)

kantan.sbt is a collection of [SBT] plugins and settings used by all kantan.projects.

## Getting started

kantan.sbt is currently available for SBT 1.0.

The current version is `2.0.0-SNAPSHOT`, which can be added to your project with the following line
in your build files (traditionally `project/plugins.sbt`, although that's not a requirement):

```scala
// Adds the core kantan.sbt plugins
addSbtPlugin("com.nrinaudo" % "kantan.sbt" % "2.0.0-SNAPSHOT")

// Adds support for sbt-release
addSbtPlugin("com.nrinaudo" % "kantan.sbt-release" % "2.0.0-SNAPSHOT")

// Adds support for scalafmt
addSbtPlugin("com.nrinaudo" % "kantan.sbt-scalafmt" % "2.0.0-SNAPSHOT")

// Adds support for scalastyle
addSbtPlugin("com.nrinaudo" % "kantan.sbt-scalastyle" % "2.0.0-SNAPSHOT")
```

## Motivation

The various kantan projects share a lot in the way they're built and deployed , from code standards
to release steps. The initial goal of kantan.sbt was to aggregate all these in a single plugin to
avoid maintaining the same tasks in, at the time of writing, 4 different projects.

The scope has somewhat changed since my current employer, [Besedo](https://besedo.com/), has started
using Scala - kantan.sbt has become the basis for our internal builds. Because of this, it's now split
in two main modules:

* `core`, which is generic and can be used by any project with minimal configuration
* `kantan`, with default configuration for kantan projects (and that is truly useless for anything
  else)

[SBT]:https://www.scala-sbt.org/
