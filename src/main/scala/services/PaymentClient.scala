package ge.zgharbi.pfps
package services

import domain.order._
import domain.payment._

trait PaymentClient[F[_]] {
  def process(payment: Payment): F[PaymentId]
}
