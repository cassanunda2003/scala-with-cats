import cats.kernel.Semigroup
import cats.Semigroupal
import cats.instances.option._

Semigroupal[Option].product(Some(123), Some("abc"))


Semigroupal[Option].product(None, Some("abc"))

Semigroupal[Option].product(Some(123), None)

Semigroupal.tuple3(Option(1), Option("abs"), Option(1))


import cats.syntax.apply._

(Option(1),Option(2)).tupled

case class Cat(name: String, born: Int, color: String)


//this uses option instances and a method Cat.apply passed to mapN to construct a cat
(
    Option("Garfield"),
    Option(1978),
    Option("Organge and Black")
).mapN(Cat.apply)




