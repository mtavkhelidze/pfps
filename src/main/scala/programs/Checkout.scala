package ge.zgharbi.pfps
package programs

import domain.cart._
import domain.order._
import domain.payment._
import domain.user._
import effects.Background
import retries.{Retriable, Retry}
import services.{Orders, PaymentClient, ShoppingCart}

import cats.MonadThrow
import cats.data.NonEmptyList
import cats.syntax.all._
import org.typelevel.log4cats.Logger
import retry._
import squants.market.Money

import scala.concurrent.duration._

final case class Checkout[F[_]: MonadThrow: Retry: Background: Logger](
  payments: PaymentClient[F],
  cart: ShoppingCart[F],
  orders: Orders[F],
  retryPolicy: RetryPolicy[F],
) {

  def process(userId: UserId, card: Card) = {
    cart.get(userId).flatMap { case CartTotal(items, total) =>
      for {
        its <- ensureNonEmpty(items)
        pid <- processPayment(Payment(userId, total, card))
        oid <- createOrder(userId, pid, its, total)
        _ <- cart.delete(userId)
      } yield oid
    }
  }

  private def createOrder(
    userId: UserId,
    paymentId: PaymentId,
    items: NonEmptyList[CartItem],
    total: Money,
  ): F[OrderId] = {
    val action: F[OrderId] = Retry[F]
      .retry(retryPolicy, Retriable.Orders)(
        orders.create(userId, paymentId, items, total),
      )
      .adaptErr { case e =>
        OrderError(e.getMessage)
      }
    def bgAction(fa: F[OrderId]): F[OrderId] =
      fa.onError { case _ =>
        Logger[F].error(s"Failed to create order for: ${paymentId.show}") *>
          Background[F].schedule(bgAction(fa), 1.hour)
      }

    bgAction(action)
  }

  private def processPayment(in: Payment): F[PaymentId] =
    Retry[F]
      .retry(retryPolicy, Retriable.Payments)(payments.process(in))
      .adaptErr { case e =>
        PaymentError(Option(e.getMessage).getOrElse("Unknown payment error."))
      }

  private def ensureNonEmpty[A](xs: List[A]): F[NonEmptyList[A]] =
    MonadThrow[F].fromOption(NonEmptyList.fromList(xs), EmptyCartError)
}
