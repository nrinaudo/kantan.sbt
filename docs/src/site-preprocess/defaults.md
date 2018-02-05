---
layout: tutorial
title: "Out of the box behaviours"
section: tutorial
sort_order: 1
---

When adding kantan.sbt as a plugin to your build, you will get a few new behaviours immediately.

## Scalac

Scalac will now use a fair amount of compiler flags.

The majority of them are additional checks and warnings - the default scalac behaviour being far
too permissive for my tastes. These go from unused imports to shadowing of function parameters. If
you're into this type of things, see the [`StrictPlugin`] for more aggressive options.

A few standard language features will also be enabled by default - macros, existentials,
higher-kinded types... because I feel they really should be part of the standard language rather
than locked away behind weird imports.

Finally, [kind-projector](https://github.com/non/kind-projector) will be added to dependencies
automatically, because it's such an amazing compiler plugin.

## Headers

All my code, either OSS or not, falls under some form of license. My OSS work is mostly
[Apache](https://www.apache.org/licenses/LICENSE-2.0), for example.

I like to include this in a header in all of my sources.
[sbt-header] is the perfect tool for this, and is enabled
by default.

Note that, due to the way [sbt-header] works, there is no trivial way to automate header
generation by default - the standard pattern is to manually enable `AutomateHeaderPlugin`
for each project. There is, however, a (rather nasty) trick to achieve this. Have a look at
`KantanAutomateHeaderPlugin` if you're interested in this.

Finally, if [sbt-boilerplate] is enabled for one of your projects, template files will also
have generated headers.

## Doctest

[sbt-doctest] is a really neat project that lets you
write sample code in your documentation and turn that code into tests. A fairly common
pattern I've started using is, when done with a bit of code, to start a REPL and manually
test a couple of things. If they work out, paste them in the documentation - free documentation
and simple tests for free! This, of course, does not replace proper unit tests, but at the very
least guarantees that your documentation's samples are always up to date.

kantan.sbt enables [sbt-doctest], with a few default configuration options. At the time of writing:

* `doctestMarkdownEnabled := true`
* `doctestTestFramework   := DoctestTestFramework.ScalaTest`

These can of course be overriden in your own buildfiles.

## Dependency resolver

[coursier] is a clear improvement on SBT's native dependency resolver, and is enabled by default
in all projects.

One concrete reason, other than it being an all around improvement, is that it works around a
blocking bug with SBT: it is, apparently, possible to have multiple incompatible versions of the
same library in the CLASSPATH.


## Validation

kantan.sbt declares a `validate` command that will run all tests, check code style, generate
all documentation, and write a coverage report. The basic purpose of this is to be executed
during CI.

Note that style checking is documented [here]({{ site.baseurl }}/style.html)




[`StrictPlugin`]:{{ site.baseurl }}/strict.html
[sbt-header]:https://github.com/sbt/sbt-header
[sbt-doctest]:https://github.com/tkawachi/sbt-doctest
[sbt-boilerplate]:https://github.com/sbt/sbt-boilerplate
[coursier]:https://github.com/coursier/coursier
