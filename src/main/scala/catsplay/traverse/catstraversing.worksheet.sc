import cats.Traverse
import cats.instances.future._
import cats.instances.list._

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

val hostnames = List(
  "a.example.com",
  "bc.example.com",
  "cdc.example.com"
)

def getUptime(hostName: String): Future[Int] =
  Future(hostName.length() * 60)

val totalUptime: Future[List[Int]] =
  Traverse[List].traverse(hostnames)(getUptime)

Await.result(totalUptime, 1.second)

Await.result(totalUptime, 1.second)
// res0: List[Int] = List(1020, 960, 840)
val numbers = List(Future(1), Future(2), Future(3))
val numbers2: Future[List[Int]] =
  Traverse[List].sequence(numbers)
Await.result(numbers2, 1.second)
// res1: List[Int] = List(1, 2, 3)

import cats.syntax.traverse._ // for sequence and traverse
Await.result(hostnames.traverse(getUptime), 1.second)
// res2: List[Int] = List(1020, 960, 840)
Await.result(numbers.sequence, 1.second)
// res3: List[Int] = List(1, 2, 3)
