---
layout: tutorial
title: "Scala.js"
section: tutorial
sort_order: 7
---

You can use kantan.sbt's [scala.js] support by adding the following SBT line:

```scala
addSbtPlugin("com.nrinaudo" % "kantan.sbt-scalajs" % "2.1.0")
```

This lets you declare cross-projects using `kantanCrossProject` rather than `crossProject`.

Calling `kantanCrossProject(id)` will:
* create a JVM project called `$id-jvm`
* create a JS project called `$id-js`
* disable `scoverage` for the JS project (because it's currently [broken](https://github.com/scoverage/scalac-scoverage-plugin/issues/196))
* disable `sbt-doctest` for the JS project (because it's currently [broken](https://github.com/tkawachi/sbt-doctest/issues/52))
* set `boilerplate` to use the `shared` source tree (until a [better alternative is found](https://github.com/sbt/sbt-boilerplate/issues/21))

[scala.js]:https://www.scala-js.org/
