---
layout: tutorial
title: "Style validation"
section: tutorial
sort_order: 2
---

## `checkStyle`

kantan.sbt adds a new `checkStyle` task that will validate your code's style.
Its default implementation is empty, but kantan.sbt provides modules for [scalafmt].

Note that `checkStyle` and `Test/checkStyle` will be executed whenever you run
`validate`.

## Scalafix

You can use kantan.sbt's [scalafmt] support by adding the following SBT line:

```scala
addSbtPlugin("com.nrinaudo" % "kantan.sbt-scalafix" % "@VERSION@")
```

In addition to all the standard [scalafix] tasks, this adds `scalafixAll`, used to run [scalafix]
on all files in your project.

Additionally, you can now use the `scalafixResource` setting to specify a resource from which
to read the scalafix configuration. This is meant to be useful for organisations that wish to share
a single configuration accross multiple projects.

## Scalafmt

You can use kantan.sbt's [scalafmt] support by adding the following SBT line:

```scala
addSbtPlugin("com.nrinaudo" % "kantan.sbt-scalafmt" % "@VERSION@")
```

In addition to all the standard [scalafmt] tasks, this adds `scalafmtAll`, used to run [scalafmt]
on all files in your project.

Additionally, you can now use the `scalafmtResource` setting to specify a resource from which
to read the scalafmt configuration. This is meant to be useful for organisations that wish to share
a single configuration accross multiple projects.

[scalafix]:https://scalacenter.github.io/scalafix/
[scalafmt]:http://scalameta.org/scalafmt/
