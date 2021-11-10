import cats.instances.int._

import cats.Monoid
import cats.syntax.semigroup._ // for |+|

def foldMap[A, B : Monoid](as: Vector[A])(func: A => B): B =
as.map(func).foldLeft(Monoid[B].empty)(_ |+| _)

foldMap(Vector(1,2,3))(identity)

foldMap("Hello world!".toVector)(_.toString.toUpperCase)