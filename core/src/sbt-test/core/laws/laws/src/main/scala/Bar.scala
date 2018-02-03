object Bar {
  def runTest(i: Int): Unit = assert(Foo.copy(i) == i)
}
