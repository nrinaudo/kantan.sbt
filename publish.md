---
layout: tutorial
title: "Publication"
section: tutorial
sort_order: 4
---

kantan.sbt adds two publication-related plugins:

* `PublishedPlugin`
* `UnpublishedPlugin`

## `PublishedPlugin`

Enabling this plugin for a project marks it as meant to be published. This is
mostly meant as a tag for other plugins to piggyback - there's a kantan-specific
plugin, for example, that will set `publishTo` to sonatype if `PublishedPlugin`
is enabled.

The general idea is that if a project expects to publish artifacts, say, to a maven
repository, it should enable this plugin.

## `UnpublishedPlugin`

Enabling this plugin will do its best to ensure nothing is published, ever, for your
project. This can be tricky, as different common plugins rely on different values, but
`UnpublishedPlugin` does its best to block them all.
