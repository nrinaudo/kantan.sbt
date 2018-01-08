---
layout: tutorial
title: "Strict compilation"
section: tutorial
sort_order: 3
---

Enabling `StrictPlugin` in a project will turn all warnings fatal - a setting that has so
far never led me astray.

Note that this is only true for the `Compile` scope:

* it's unfortunately too risky to enable it for `Test`, since some tests voluntarily cause
  warnings - I sometimes write tests that ensure the right `implicit` instance of something
  is picked up, for example, and these trigger the `unused:implicits` warning.
* all other scopes are ignored because of potentially unexpected side effects - the REPL
  becomes unusable, for example, or [tut] just stops working.

Additionally, `StrictPlugin` enables [wartremover] with a safe set of rules - that is, rules
that have never yielded a false positive on any project I've ran them on, and that do not catch
symptoms I don't think are problematic.


[wartremover]:https://github.com/wartremover/wartremover
[tut]:https://github.com/tpolecat/tut
