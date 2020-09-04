package catsplay.functors


import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers
import cats.syntax.functor._

class TreeSpec extends AnyFreeSpec with Matchers {

    "TreeFunctor must" - {
        "give map method" in {
          Tree.leaf(100).map(_ * 2) mustBe Leaf(200)
          Tree.branch(Tree.leaf(10), Tree.leaf(20)).map(_ * 2) mustBe Branch(Leaf(20),Leaf(40))
        }
    }
}
