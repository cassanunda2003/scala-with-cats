package exercises.chap2

import cats.Monoid

final case class Orders(cost: Double, quantity: Int)

object Orders {
    implicit val  orderMonoid: Monoid[Orders]  =  new Monoid[Orders] {
        def combine(x: Orders, y: Orders): Orders = Orders(
            x.cost + y.cost,
            x.quantity + y.quantity
        )

        def empty: Orders = Orders(0, 0)
    }
}