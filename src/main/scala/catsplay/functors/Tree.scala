package catsplay.functors

import cats.Functor

trait  Tree[+A] 

final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

final case class Leaf[A](values: A) extends Tree[A]


object Tree {
    implicit val TreeFunctor: Functor[Tree] = new Functor[Tree] {
        def map[A, B](fa: Tree[A])(f: A => B): Tree[B] = {
            fa match {
               case Branch(left, right) => Branch(map(left)(f), map(right)(f))
               case Leaf(values) => Leaf(f(values))
            }
        }
    }

    def branch[A](left: Tree[A], right: Tree[A]): Tree[A] =
            Branch(left, right)
    
    def leaf[A](value: A): Tree[A] =
        Leaf(value)
}

