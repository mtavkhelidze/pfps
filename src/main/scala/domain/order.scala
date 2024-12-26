package ge.zgharbi.pfps
package domain

import domain.cart._
import domain.item._

import derevo.cats.{eqv, show}
import derevo.circe.magnolia.{decoder, encoder}
import derevo.derive
import io.estatico.newtype.macros.newtype
import squants.market.Money

import java.util.UUID
import scala.util.control.NoStackTrace

object order {

  @derive(show)
  sealed trait OrderOrPaymentError extends NoStackTrace {
    def cause: String
  }

  @derive(eqv, show)
  case class OrderError(cause: String) extends OrderOrPaymentError

  @derive(eqv, show)
  case class PaymentError(cause: String) extends OrderOrPaymentError

  @derive(encoder, decoder, show)
  @newtype case class OrderId(uuid: UUID)

  @derive(encoder, decoder, show)
  @newtype case class PaymentId(uuid: UUID)

  @derive(encoder, decoder)
  case class Order(
    id: OrderId,
    pid: PaymentId,
    items: Map[ItemId, Quantity],
    total: Money,
  )
}
