package ge.zgharbi.pfps
package algebrae

import algebrae.Auth.UserId
import algebrae.Cart.{CartItem, Quantity}
import algebrae.Items.ItemId
import algebrae.Orders._

import cats.data.NonEmptyList
import derevo.cats.{eqv, show}
import derevo.derive
import io.estatico.newtype.macros.newtype
import squants.market.Money

import java.util.UUID
import scala.util.control.NoStackTrace

@derive(show)
sealed trait OrderOrPaymentError extends NoStackTrace {
  def cause: String
}

@derive(eqv, show)
case class OrderError(cause: String) extends OrderOrPaymentError
@derive(eqv, show)
case class PaymentError(cause: String) extends OrderOrPaymentError

object Orders {
  @newtype case class OrderId(uuid: UUID)
  @newtype case class PaymentId(uuid: UUID)
}
case class Order(
  id: OrderId,
  pid: PaymentId,
  items: Map[ItemId, Quantity],
  total: Money,
)

trait Orders[F[_]] {
  def get(userId: UserId, orderId: OrderId): F[Option[Order]]
  def findBy(userId: UserId): F[List[Order]]
  def create(
    userId: UserId,
    paymentId: PaymentId,
    items: NonEmptyList[CartItem],
    total: Money,
  ): F[OrderId]
}
