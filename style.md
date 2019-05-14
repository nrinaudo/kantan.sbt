---
layout: tutorial
title: "Style validation"
section: tutorial
sort_order: 2
---

## `checkStyle`

kantan.sbt adds a new `checkStyle` task that will validate your code's style.
Its default implementation is empty, but kantan.sbt provides modules for [scalafmt]
and [scalastyle].

Note that `checkStyle` and `test:checkStyle` will be executed whenever you run
`validate`.


## Scalafmt

You can use kantan.sbt's [scalafmt] support by adding the following SBT line:

```scala
addSbtPlugin("com.nrinaudo" % "kantan.sbt-scalafmt" % "2.3.1")
```

In addition to all the standard [scalafmt] tasks, this adds `scalafmtAll`, used to run [scalafmt]
on all files in your project.

Additionally, you can now use the `scalafmtResource` setting to specify a resource from which
to read the scalafmt configuration. This is meant to be useful for organisations that wish to share
a single configuration accross multiple projects.

## Scalastyle

You can use kantan.sbt's [scalastyle] support by adding the following SBT line:

```scala
addSbtPlugin("com.nrinaudo" % "kantan.sbt-scalastyle" % "2.3.1")
```

This allows you to use the `scalastyleResource` setting to specify a resource from which
to read the scalastyle configuration. This is meant to be useful for organisations that wish
to share a single configuration accross multiple projects.


[scalafmt]:http://scalameta.org/scalafmt/
[scalastyle]:http://www.scalastyle.org/
