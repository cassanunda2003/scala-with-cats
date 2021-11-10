package chap8

import cats.Id
import org.scalatest.freespec.AnyFreeSpec
import scala.concurrent.Future
import org.scalatest.matchers.must.Matchers

class UptimeServiceSpec extends AnyFreeSpec with Matchers {

  class TestUptimeClient(hosts: Map[String, Int]) extends UptimeClient[Id] {
    def getUptime(hostname: String): Int =
      hosts.getOrElse(hostname, 0)
  }

  "test total uptime" - {
    "uptime should equal 16" in {

      val hosts = Map("host1" -> 10, "host2" -> 6)
      val client = new TestUptimeClient(hosts)
      val service = new UptimeService(client)
      val actual = service.getTotalUptime(hosts.keys.toList)
      val expected = hosts.values.sum
      actual mustBe expected
    }
  }
}
