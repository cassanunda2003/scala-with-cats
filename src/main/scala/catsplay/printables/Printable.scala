package catsplay.printables

import sandbox.chapter1.Cat
import catsplay.Box

sealed trait Printable[A] {
  self =>
  def format(value: A): String = ???

  //exercise 3.6.1.1
  def contramap[B](func: B => A): Printable[B] = 
    new Printable[B] {
        override def format(value: B): String = {
          //I want to use the format from printable A so use self tag

            self.format(func(value))
        }

    }
}

object PrintableInstances {
  implicit val printableString: Printable[String] =
    new Printable[String] {
      override def format(value: String): String = value
    }
  implicit val printableInt: Printable[Int] = new Printable[Int] {
    override def format(value: Int): String = value.toString
  }
  implicit val printableCat: Printable[Cat] = new Printable[Cat] {
    override def format(value: Cat): String = s"${value.name} is a ${value.age} year-old ${value.colour} cat."
  }
  implicit val booleanPrintable: Printable[Boolean] =  new Printable[Boolean] {
    override def format(value: Boolean): String = if (value) "yes" else "no"
  }
  implicit def boxPrintable[A](implicit p: Printable[A]) = {
      p.contramap[Box[A]](_.value)

  }
}

object PrintableSyntax {
  implicit class PrintableOps[A](value: A) {
    def format(implicit p: Printable[A]): String =
     p.format(value)

    def print(implicit p: Printable[A]): Unit = 
    println(format(p))
  }
}

object Printable {
  def format[A](value: A)(implicit printable: Printable[A]) = printable.format(value)
  def print[A](value: A)(implicit printable: Printable[A]): Unit = println(printable.format(value))
}
