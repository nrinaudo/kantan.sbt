import org.scalatest.funsuite.AnyFunSuite

class FooTest extends AnyFunSuite {
  test("Foo.copy should return its input") {
    assert(Foo.copy(1) == 1)
  }
}
