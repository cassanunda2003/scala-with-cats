package sandbox.json

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import catsplay.printables._
import catsplay.printables.PrintableInstances._

class PrintableTest extends AnyFreeSpec with Matchers {

  "Printable must " - {
    "Output the correct message for a cat" in {
      val cat = sandbox.chapter1.Cat("Jerry",3,"Black")
      Printable.format(cat) mustBe "Jerry is a 3 year-old Black cat."
    }
  }
}
