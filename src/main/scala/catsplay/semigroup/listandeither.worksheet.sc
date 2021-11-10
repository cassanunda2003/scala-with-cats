import cats.Semigroupal
import cats.instances.list._

Semigroupal[List].product(List(1,2), List(2,5))

//A cartesian produc not a zip

//Either fails fast as with normal either



import cats.instances.either._ // for Semigroupal

type ErrorOr[A] = Either[Vector[String], A]

Semigroupal[ErrorOr].product(
    Left(Vector("Error 1")),
    Left(Vector("Error 2"))
)


