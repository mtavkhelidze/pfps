package ge.zgharbi.pfps
package domain

import domain.Auth.UserId
import domain.Order.PaymentId

import squants.market.Money

trait Card

case class Payment(id: UserId, total: Money, card: Card)

trait PaymentClient[F[_]] {
  def process(payment: Payment): F[PaymentId]
}
