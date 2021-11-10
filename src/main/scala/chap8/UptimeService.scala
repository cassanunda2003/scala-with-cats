package chap8

import cats.instances.future._
import cats.instances.list._
import cats.syntax.traverse._
import cats.Applicative
import cats.syntax.functor._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UptimeService[F[_]](client: UptimeClient[F])(implicit a: Applicative[F]) {

  def getTotalUptime(hostnames: List[String]): F[Int] = {
    hostnames.traverse(client.getUptime).map(_.sum)
  }

}
