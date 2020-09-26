def parseInt(str: String): Option[Int] = scala.util.Try(str.toInt).toOption

def divide(a: Int, b: Int): Option[Int] = if(b==0) None else Some(a/b)

def stringDivideBy(aStr: String, bStr: String): Option[Int] = 
    parseInt(aStr).flatMap { aNum => 
        parseInt(bStr).flatMap { bNum =>
            divide(aNum, bNum)
        }
    }

def stringDivideByFor(aStr: String, bStr: String): Option[Int] = 
    for {
        aNum <- parseInt(aStr) 
        bNum <- parseInt(bStr)
        ans <- divide(aNum, bNum)
    } yield ans
    
stringDivideBy("6","2")
stringDivideBy("a","5")
stringDivideBy("6","c")
stringDivideBy("6","0")

stringDivideByFor("6","2")
stringDivideByFor("a","5")
stringDivideByFor("6","c")
stringDivideByFor("6","0")

for {
    x <- (1 to 3).toList
    y <- (4 to 5).toList
} yield (x, y)

import cats.Monad
import cats.instances.option._
import cats.instances.list._
import cats.instances.int._
import cats.syntax.functor._
import cats.syntax.flatMap._

val opt1 = Monad[Option].pure(3)
val opt2 = Monad[Option].flatMap(opt1)(a => Some(a + 2))
val opt3 = Monad[Option].map(opt2)(a => 100 * a)


val list1 = Monad[List].pure(3)

val list2 = Monad[List].flatMap(List(1, 2, 3))(a => List(a, a*10))
val list3 = Monad[List].map(list2)(a => a + 123)

def sumSquare[F[_]: Monad](a: F[Int], b: F[Int]): F[Int] =
 a.flatMap(x => b.map(y => x*x + y*y))

 sumSquare(Option(3),Option(4))

 def sumSquareFor[F[_]: Monad](a: F[Int], b: F[Int]): F[Int] =
    for {
        x <- a
        y <- b
    } yield x*x + y*y

 sumSquareFor(Option(3),Option(4))

 import cats.Id

 sumSquare(3: Id[Int], 4: Id[Int])

 "Dave" : Id[String]
 
 import cats.syntax.either._

 val a = 3.asRight[String]

 val b = 4.asRight[String]

 for {
     x <- a
     y <- b
 } yield x*x + y*y


 def countPositive(nums: List[Int]) =
    nums.foldLeft(0.asRight[String]) { (accumulator, num) =>
        if(num > 0) {
            accumulator.map(_ + 1)
        } else {
            Left("Negative. Stopping!")
        }
    }

    countPositive(List(1,2,3))

    countPositive(List(1,-2,3))

 Either.catchOnly[NumberFormatException]("foo".toInt)
 
 Either.catchNonFatal(sys.error("Badness"))

 Either.fromTry(scala.util.Try("foo".toInt))

 Either.fromOption[String, Int](None, "Badness")

 for {
     a <- 1.asRight[String]
     b <- 0.asRight[String]
     c <- if(b == 0) "DIV0".asLeft[Int]
            else (a/b).asRight[String]
 } yield c * 100

 object wrapper {
        sealed trait LoginError extends Product with Serializable
        final case class UserNotFound(username: String)
        extends LoginError
        final case class PasswordIncorrect(username: String)
        extends LoginError
        case object UnexpectedError extends LoginError
} 

import wrapper._
case class User(username: String, password: String)
type LoginResult = Either[LoginError, User]

def handleError(error: LoginError): Unit =
    error match {
        case UserNotFound(u) =>
          println(s"User not found: $u")
        case PasswordIncorrect(u) =>
           println(s"Password incorrect: $u")
        case UnexpectedError =>
           println(s"Unexpected error")
}

val result1: LoginResult = User("dave", "passw0rd").asRight

val result2: LoginResult = UserNotFound("dave").asLeft

result1.fold(handleError, println)
// User(dave,passw0rd)
result2.fold(handleError, println)


