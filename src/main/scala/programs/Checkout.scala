package ge.zgharbi.pfps
package programs

import algebrae._
import algebrae.Auth.UserId
import algebrae.Cart.EmptyCartError
import algebrae.Orders.PaymentId
import retries.Retriable.Payments
import retries.Retry
import services.ShoppingCart

import cats.MonadThrow
import cats.data.NonEmptyList
import cats.syntax.all._
import retry.RetryPolicy

final case class Checkout[F[_]: MonadThrow: Retry](
  payments: PaymentClient[F],
  cart: ShoppingCart[F],
  orders: Orders[F],
  retryPolicy: RetryPolicy[F],
) {
  def processPayment(in: Payment): F[PaymentId] =
    Retry[F]
      .retry(retryPolicy, Payments)(payments.process(in))
      .adaptErr { case e =>
        PaymentError(Option(e.getMessage).getOrElse("Unknown payment error."))
      }

  def process(userId: UserId, card: Card) =
    for {
      c <- cart.get(userId)
      its <- ensureNonEmpty(c.items)
      pid <- processPayment(Payment(userId, c.total, card))
      pid <- orders.create(userId, pid, its, c.total)
      _ <- cart.delete(userId)
    } yield pid

  private def ensureNonEmpty[A](xs: List[A]): F[NonEmptyList[A]] =
    MonadThrow[F].fromOption(NonEmptyList.fromList(xs), EmptyCartError)
}
