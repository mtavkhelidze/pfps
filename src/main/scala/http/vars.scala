package ge.zgharbi.pfps
package http

import domain.item.ItemId
import domain.order.OrderId

import cats.implicits._

import java.util.UUID

object vars {
  protected class UUIDVar[A](f: UUID => A) {
    def unapply(str: String): Option[A] =
      Either.catchNonFatal(f(UUID.fromString(str))).toOption
  }

  object ItemIdVar extends UUIDVar(ItemId.apply)
  object OrderIdVar extends UUIDVar(OrderId.apply)
}
