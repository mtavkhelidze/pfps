package ge.zgharbi.pfps
package domain

import domain.item.{Item, ItemId}

import derevo.cats.show
import derevo.derive
import io.estatico.newtype.macros.newtype
import squants.market.Money

import scala.util.control.NoStackTrace

object cart {
  @newtype case class Quantity(value: Int)

  @newtype case class Cart(items: Map[ItemId, Quantity])

  case class CartItem(item: Item, quantity: Quantity)

  case class CartTotal(items: List[CartItem], total: Money)

  @derive(show)
  case object EmptyCartError extends NoStackTrace
}
