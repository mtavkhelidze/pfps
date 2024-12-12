package ge.zgharbi.pfps
package algebrae

import algebrae.Auth.UserId
import algebrae.Items.ItemId
import algebrae.ShoppingCart.{Cart, Quantity}

import io.estatico.newtype.macros.newtype
import squants.market.Money

object ShoppingCart {
  @newtype case class Quantity(value: Int)
  @newtype case class Cart(items: Map[ItemId, Quantity])
}

case class CartItem(item: Item, quantity: Quantity)
case class CartTotal(items: List[CartItem], total: Money)

trait ShoppingCart[F[_]] {
  def add(userId: UserId, itemId: ItemId, quantity: Quantity): F[Unit]
  def get(userId: UserId): F[CartTotal]
  def delete(userId: UserId): F[Unit]
  def removeItem(userId: UserId, itemId: ItemId): F[Unit]
  def update(userId: UserId, cart: Cart): F[Unit]
}
