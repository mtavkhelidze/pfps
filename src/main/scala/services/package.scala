package ge.zgharbi.pfps

import domain.brand._
import domain.category._
import domain.item._

import eu.timepit.refined.types.string.NonEmptyString
import skunk._
import skunk.codec.all._
import squants.Money
import squants.market.USD

package object services {
  object codecs {
    val brandId: Codec[BrandId] = uuid.imap(BrandId.apply)(_.value)
    val brandName: Codec[BrandName] = varchar.imap(BrandName.apply)(_.value)

    val categoryId: Codec[CategoryId] = uuid.imap(CategoryId.apply)(_.value)
    val categoryName: Codec[CategoryName] =
      varchar.imap(CategoryName.apply)(_.value)

    val itemId: Codec[ItemId] = uuid.imap[ItemId](ItemId(_))(_.value)
    val itemName: Codec[ItemName] =
      varchar.imap[ItemName](s => ItemName(s.asInstanceOf[NonEmptyString]))(
        _.value.toString,
      )
    val itemDesc: Codec[ItemDescription] =
      varchar.imap[ItemDescription](s =>
        ItemDescription(s.asInstanceOf[NonEmptyString]),
      )(_.value.toString)

    val money: Codec[Money] = numeric.imap[Money](USD(_))(_.amount)
  }
}
