package ge.zgharbi.pfps
package domain

import domain.item.{Item, ItemId}
import domain.user.UserId

import derevo.cats._
import derevo.circe.magnolia.{decoder, encoder}
import derevo.derive
import io.circe.{Decoder, Encoder}
import io.estatico.newtype.macros.newtype
import squants.market.Money

import scala.util.control.NoStackTrace

object cart {

  @derive(encoder, decoder, show)
  @newtype case class Quantity(value: Int)

  @derive(encoder, decoder, show)
  case class CartItem(item: Item, quantity: Quantity)

  @derive(encoder, decoder, show)
  case class CartTotal(items: List[CartItem], total: Money)

  @newtype case class Cart(items: Map[ItemId, Quantity])
  object Cart {
    import io.circe.generic.auto._
    implicit val jsonEncoder: Encoder[Cart] =
      Encoder.forProduct1("items")(_.items)

    implicit val jsonDecoder: Decoder[Cart] =
      Decoder.forProduct1("items")(Cart.apply)
  }

  @derive(show)
  case object EmptyCartError extends NoStackTrace

  @derive(decoder, encoder, show)
  case class CartNotFound(userId: UserId) extends NoStackTrace
}
