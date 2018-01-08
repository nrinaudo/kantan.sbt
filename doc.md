---
layout: tutorial
title: "Documentation"
section: tutorial
sort_order: 5
---

kantan.sbt adds a new `DocumentationPlugin`. Enabling it on a project will:

* mark it as [`Unpublished`]({{ site.baseurl }}/publish.html)
* enable [sbt-unidoc](https://github.com/sbt/sbt-unidoc)
* enable [sbt-site](https://github.com/sbt/sbt-site)'s `PreprocessPlugin`
* enable [tut](https://github.com/tpolecat/tut)
* enable [sbt-ghpages](https://github.com/sbt/sbt-ghpages)

All these plugins put together mean that, when you run `makeSite`:

* all files in `src/site` will be copied to `target/site`
* all files in `src/main/tut` will be [preprocessed](http://www.scala-sbt.org/sbt-site/preprocess.html)
  and tut-compiled, and the output copied to `target/site`

Running `ghpagesPushSite` will then push this to your project ghpages branch.

This requires the `git.remoteRepo` setting to be set to your project's URI on github.

Additionally, if `scmInfo` is set, your scaladoc will link to your project's source on github.
