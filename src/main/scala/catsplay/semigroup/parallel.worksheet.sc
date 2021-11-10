import cats.Foldable
import cats.Semigroupal
import cats.instances.either._

//Stops at first error
type ErrorOr[A] = Either[Vector[String], A]
val error1: ErrorOr[Int] = Left(Vector("Error 1"))
val error2: ErrorOr[Int] = Left(Vector("Error 2"))

Semigroupal[ErrorOr].product(error1, error2)

import cats.syntax.apply._
import cats.instances.vector._

(error1, error2).tupled
 
//To get all user parallel

import cats.syntax.parallel._
(error1, error2).parTupled

//parMapN used to feed functions

val success1: ErrorOr[Int] = Right(1)
val success2: ErrorOr[Int] = Right(2)
val addTwo = (x: Int, y: Int) => x + y

(error1, error2).parMapN(addTwo)

(success1, success2).parMapN(addTwo)

//This work by converting Monad to Applicative
//using FunctionK

//FunctionK to convert Option to List
import cats.arrow.FunctionK

object optionToList extends FunctionK[Option, List] {
    def apply[A](fa: Option[A]): List[A] = 
        fa match {
            case None => List.empty[A]
            case Some(a) => List(a)
        }
}

optionToList(Some(1))

optionToList(None)

import cats.instances.int._

Foldable[List].combineAll(List(1,2,3))

import cats.instances.string._

Foldable[List].foldMap(List(1,2,3))(_.toString)

import cats.instances.vector._

val ints = List(Vector(1,2,3), Vector(4,5,6))

(Foldable[List] compose Foldable[Vector]).combineAll(ints)

import cats.syntax.foldable._

List(1,2,3).combineAll


