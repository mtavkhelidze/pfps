package ge.zgharbi.pfps
package domain

import domain.item.{Item, ItemId}

import derevo.cats._
import derevo.circe.magnolia.{decoder, encoder}
import derevo.derive
import io.circe.{Decoder, Encoder}
import io.estatico.newtype.macros.newtype
import squants.market.Money

import scala.util.control.NoStackTrace

object cart {

  @derive(encoder, decoder)
  @newtype case class Quantity(value: Int)

  @derive(encoder, decoder)
  case class CartItem(item: Item, quantity: Quantity)

  @derive(encoder, decoder)
  case class CartTotal(items: List[CartItem], total: Money)

//  @derive(decoder)
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
}
