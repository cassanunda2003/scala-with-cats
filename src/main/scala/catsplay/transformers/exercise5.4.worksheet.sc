import cats.data.EitherT
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
type Response[A] = EitherT[Future, String, A]
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import cats.instances.future._


val powerLevels = Map(
    "Jazz" -> 6,
    "Bumblebee" -> 8,
    "Hot Rod" -> 10
)



def getPowerLevel(autobot: String): Response[Int] = {
    powerLevels.get(autobot) match {
        case Some(num) => EitherT.right(Future(num))
        case None => EitherT.left(Future(autobot))
     }
}

def canSpecialMove(ally1: String, ally2: String): Response[Boolean] = 
    for {
        a1 <- getPowerLevel(ally1)
        a2 <- getPowerLevel(ally2)
    } yield (a1 + a2 > 15)

def tacticalReport(ally1: String, ally2: String): String ={ 
    val res = canSpecialMove(ally1, ally2).value.map {
        res => res match {
            case Right(value) => 
                if (value) 
                    s" $ally1 and $ally2 are ready to roll"
                else
                   s" $ally1 and $ally2 need a recharge"    
            case Left(value) => s"Comms error: $value unreachable"
        }

    }
    Await.result(res, 2.seconds)
}

getPowerLevel("Jazz").value

canSpecialMove("Jazz", "Bumblebee")


tacticalReport("Jazz", "Bumblebee")

tacticalReport("Bumblebee", "Hot Rod")

tacticalReport("Jazz", "Ironhide")




