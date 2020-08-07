
object IntsAndOptions {
    def parseInt(str: String): Option[Int] =
        scala.util.Try(str.toInt).toOption

    def divide(a: Int, b: Int): Option[Int] = 
        if (b==0) None else Some(a/b)

    def stringDivideBy(aStr: String, bStr: String): Option[Int] = 
        parseInt(aStr).flatMap { aNum => 
            parseInt(bStr).flatMap{
                 bNum => 
                divide(aNum, bNum)
            }
        }
    
    def stringDivideByFor(aStr: String, bStr: String): Option[Int] = 
        for {
            aNum <- parseInt(aStr)
            bNum <- parseInt(bStr)
            ans <- divide(aNum, bNum)
        } yield ans
    
}

import IntsAndOptions._
stringDivideByFor("10","5")

stringDivideByFor("6","0")

stringDivideBy("10","5")

stringDivideBy("6","0")


import cats.Monad
import cats.instances.option._
import cats.instances.list._

val opt1 = Monad[Option].pure(3)
val opt2 = Monad[Option].flatMap(opt1)(a => Some(a + 2))
val opt3 = Monad[Option].map(opt2)(a => 100 * a)

val list1 = Monad[List].pure(3)
val list2 = Monad[List].flatMap(List(1,2,3))(a => List(a, a*10))
val list3 = Monad[List].map(list2)(a => a + 123)

List(1,2,3).flatMap(a => List(a, a*10))
List(1,2,3).map(a => List(a, a*10))


