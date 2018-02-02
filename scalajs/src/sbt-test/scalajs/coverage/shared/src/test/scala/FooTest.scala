import org.scalatest.FunSuite

class FooTest extends FunSuite {
  test("Foo.copy should return its input") {
    assert(Foo.copy(1) == 1)
  }
}
