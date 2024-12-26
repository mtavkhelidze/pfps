package ge.zgharbi.pfps
package domain

import domain.checkout.Card
import domain.user._

import derevo.circe.magnolia.{decoder, encoder}
import derevo.derive
import squants.market.Money

object payment {
  @derive(encoder, decoder)
  case class Payment(id: UserId, total: Money, card: Card)
}
