package ge.zgharbi.pfps
package programs

import algebrae._
import algebrae.Auth.UserId
import algebrae.Cart.EmptyCartError
import services.ShoppingCart

import cats.MonadThrow
import cats.data.NonEmptyList
import cats.syntax.all._

final case class Checkout[F[_]: MonadThrow](
  payments: PaymentClient[F],
  cart: ShoppingCart[F],
  orders: Orders[F],
) {
  def process(userId: UserId, card: Card) =
    for {
      c <- cart.get(userId)
      its <- ensureNonEmpty(c.items)
      pid <- payments.process(Payment(userId, c.total, card))
      pid <- orders.create(userId, pid, its, c.total)
      _ <- cart.delete(userId)
    } yield pid

  private def ensureNonEmpty[A](xs: List[A]): F[NonEmptyList[A]] =
    MonadThrow[F].fromOption(NonEmptyList.fromList(xs), EmptyCartError)
}
