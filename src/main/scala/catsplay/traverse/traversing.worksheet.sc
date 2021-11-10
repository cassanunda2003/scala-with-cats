import cats.Applicative
import cats.instances.future._
import cats.syntax.applicative._

import cats.syntax.apply._

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

def newCombine(accum: Future[List[Int]], host: String): Future[List[Int]] =
  (accum, getUptime(host)).mapN(_ :+ _)

def listTraverse[F[_]: Applicative, A, B](
    list: List[A]
)(func: A => F[B]): F[List[B]] =
  list.foldLeft(List.empty[B].pure[F]) { (accum, item) =>
    (accum, func(item)).mapN(_ :+ _)
  }

def listSequence[F[_]: Applicative, B](list: List[F[B]]): F[List[B]] =
  listTraverse(list)(identity)

val totalUptime = listTraverse(hostnames)(getUptime)

Await.result(totalUptime, 1.second)

import cats.instances.vector._

listSequence(List(Vector(1, 2, 3), Vector(4, 5, 6)))

listSequence(List(Vector(1, 2), Vector(3, 4), Vector(5, 6)))

import cats.instances.option._ // for Applicative

def process(inputs: List[Int]) =
  listTraverse(inputs)(n => if (n % 2 == 0) Some(n) else None)

process(List(2, 4, 6))
process(List(1, 2, 3))


import cats.data.Validated
import cats.instances.list._

type ErrorsOr[A] = Validated[List[String], A]

def processVal(inputs: List[Int]): ErrorsOr[List[Int]] =
    listTraverse(inputs) { n =>
        if(n % 2 == 0) {
            Validated.valid(n)
        } else {
            Validated.invalid(List(s"$n is not even"))
        }
    }

processVal(List(2,4,6))
processVal(List(1,2,3))


//These only work with one type of sequence list

