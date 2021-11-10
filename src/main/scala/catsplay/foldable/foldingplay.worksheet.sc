def show[A](list: List[A]): String = list.foldLeft("nil")((accum, item) => s"$item then $accum")

show(Nil)

show(List(1,2,3,4))

//Equal if operation is associative as + is
List(1,2,3,4).foldLeft(0)(_ + _)
List(1,2,3,4).foldRight(0)(_ + _)

//if not the different results
List(1,2,3,4).foldLeft(0)(_ - _)
List(1,2,3,4).foldRight(0)(_ - _)


List(1,2,3).foldLeft(List.empty[Int])((a, i) => i :: a)

import cats.Foldable
import cats.instances.list._

val ints = List(1,2,3)

Foldable[List].foldLeft(ints,0)(_ + _)

import cats.instances.option._

val maybeInt = Option(123)

val mayNone: Option[Int] = None

Foldable[Option].foldLeft(maybeInt, 10)(_ * _)
Foldable[Option].foldLeft(mayNone, 10)(_ * _)

import cats.Eval
import cats.Foldable

def bigData = (1 to 1000).to(LazyList)

//bigData.foldRight(0L)(_ + _)

val eval: Eval[Long] = Foldable[LazyList].foldRight(bigData, Eval.now(0L)) {
    (num, eval) =>
        eval.map(_ + num)
}

eval.value