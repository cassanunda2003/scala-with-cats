package exercises.chap3

import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers


class FunctorTesting  extends AnyFreeSpec with Matchers  {
  "Functors must" - {
      "List int map creates list of incremented values" in {
          List(1,2,3).map(n => n + 1) mustBe List(2,3,4)
      }
      "Apply successive maps" in {
          List(1, 2, 3).
            map(n => n + 1).
            map(n => n * 2).
            map(n => s"${n}!") mustBe List("4!", "6!", "8!")
      }
      "Compute futures correctly" in {
            import scala.concurrent.{Future, Await}
            import scala.concurrent.ExecutionContext.Implicits.global
            import scala.concurrent.duration._
            
            val future: Future[String] =
            Future(123).
            map(n => n + 1).
            map(n => n * 2).
            map(n => s"${n}!")
            Await.result(future, 1.second) mustBe "248!"
            // res2: String = "248!"
      }
  }

}
