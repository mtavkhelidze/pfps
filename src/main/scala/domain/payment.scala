package ge.zgharbi.pfps
package domain

import domain.user._

import squants.market.Money

object payment {
  trait Card

  case class Payment(id: UserId, total: Money, card: Card)
}
