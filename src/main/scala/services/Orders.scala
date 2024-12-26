package ge.zgharbi.pfps
package services

import domain.cart.CartItem
import domain.order.{Order, OrderId, PaymentId}
import domain.user.UserId

import cats.data.NonEmptyList
import squants.market.Money

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
