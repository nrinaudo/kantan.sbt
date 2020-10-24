---
layout: tutorial
title: "Release"
section: tutorial
sort_order: 6
---

You can use kantan.sbt's [sbt-release] support by adding the following SBT line:

```scala
addSbtPlugin("com.nrinaudo" % "kantan.sbt-release" % "2.7.4")
```

This declares the following additional release steps:

* `runCoverageOff`, to ensure that code instrumentation is turned off (I've published
  instrumented bytecode too often).
* `runCheckStyle`, to run [`checkStyle`]({{ site.baseurl }}/style.html).
* `runPushSite`, to run [sbt-ghpage](https://github.com/sbt/sbt-ghpages)'s
  `ghpagesPushSite` task.

The default release process will be updated to incorporate both `runCoverageOff` and
`runCheckStyle`.

[sbt-release]:https://github.com/sbt/sbt-release
