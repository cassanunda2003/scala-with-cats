//This provides a way to run futures in paralell instead of
//sequentialy as with flatMap, map or for

import cats.Semigroupal
import cats.instances.future._ // for Semigroupal
import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

val futurePair = Semigroupal[Future].product(Future("Hello"), Future(123))

Await.result(futurePair, 1.second)

//Futures run in paralell and then passed to a function

import cats.syntax.apply._ // for mapN
case class Cat(
    name: String,
    yearOfBirth: Int,
    favoriteFoods: List[String]
)

val futureCat = (
    Future("Garfield"),
    Future(1978),
    Future(List("Lasagne"))
).mapN(Cat.apply)

Await.result(futureCat, 1.second)

//Basically the same as the Option example

