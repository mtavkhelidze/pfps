package ge.zgharbi.pfps
package algebrae

import algebrae.Auth.UserId
import algebrae.Orders.PaymentId

import squants.market.Money

trait Card

case class Payment(id: UserId, total: Money, card: Card)

trait PaymentClient[F[_]] {
  def process(payment: Payment): F[PaymentId]
}
