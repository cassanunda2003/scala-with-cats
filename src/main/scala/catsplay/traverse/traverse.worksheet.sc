
//example traversing futures

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

val hostnames = List(
    "a.example.com",
    "bc.example.com",
    "cdc.example.com",
)

def getUptime(hostName: String): Future[Int] = 
    Future(hostName.length() * 60)

//mapping over hostnames gives more than one future

//try a fold

val allUptimes: Future[List[Int]] =
    hostnames.foldLeft(Future(List.empty[Int])) {
        (accum, host) =>
            val uptime = getUptime(host)
            for {
                accum <- accum
                uptime <- uptime
            } yield accum :+ uptime
    }


//We have to combine the futures using a for comp
Await.result(allUptimes, 1.second)

//Future traverse makes the pattern easier


val allUptimes2 = Future.traverse(hostnames)(getUptime)

Await.result(allUptimes2, 1.second)

//Traverse is basically the code in the first allUptimes abstracted into a function

//Sequence uses identity implicitly to convert List[Future[A]] into Future[List[A]]




    