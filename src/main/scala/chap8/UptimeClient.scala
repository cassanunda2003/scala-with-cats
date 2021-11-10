package chap8

import cats.Id
import scala.concurrent.Future

trait UptimeClient[F[_]] {
    //how to throw away future part for test client
  def getUptime(hostname: String): F[Int]
}

trait TestUptimeClient extends UptimeClient[Id] {
    def getUptime(hostname: String): Int //Id is an alias for A
}

trait RealUptimeClient extends UptimeClient[Future] {
    def getUptime(hostname: String) : Future[Int]
}
