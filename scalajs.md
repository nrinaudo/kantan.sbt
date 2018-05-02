---
layout: tutorial
title: "Scala.js"
section: tutorial
sort_order: 7
---

You can use kantan.sbt's [scala.js] support by adding the following SBT line:

```scala
addSbtPlugin("com.nrinaudo" % "kantan.sbt-scalajs" % "2.2.1")
```

This lets you declare cross-projects using `kantanCrossProject` rather than `crossProject`.

Calling `kantanCrossProject(id)` will:
* create a JVM project called `$id-jvm`
* create a JS project called `$id-js`
* disable `scoverage` for the JS project (because it's currently [broken](https://github.com/scoverage/scalac-scoverage-plugin/issues/196))
* disable `sbt-doctest` for the JS project (because it's currently [broken](https://github.com/tkawachi/sbt-doctest/issues/52))
* set `boilerplate` to use the `shared` source tree (until a [better alternative is found](https://github.com/sbt/sbt-boilerplate/issues/21))

Additionally, the plugin declares the following new tasks:
* `checkStyleJS`: runs `checkStyle` on all JS projects
* `checkStyleJVM`: runs `checkStyle` on all JVM projects
* `testJS`: runs tests on all JS projects
* `testJVM`: runs tests on all JVM projects

Finally, the two following commands are added:
* `validateJS`: runs validation on all JS projects. This does not include code coverage or documentation
* `validateJVM`: runs full validation on all JVM projects.

[scala.js]:https://www.scala-js.org/
