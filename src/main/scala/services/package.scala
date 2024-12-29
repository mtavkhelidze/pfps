package ge.zgharbi.pfps

import domain.brand.{BrandId, BrandName}
import domain.category.{CategoryId, CategoryName}

import skunk._
import skunk.codec.all._

package object services {
  object codecs {
    val brandId: Codec[BrandId] = uuid.imap(BrandId.apply)(_.value)
    val brandName: Codec[BrandName] = varchar.imap(BrandName.apply)(_.value)

    val categoryId: Codec[CategoryId] = uuid.imap(CategoryId.apply)(_.value)
    val categoryName: Codec[CategoryName] =
      varchar.imap(CategoryName.apply)(_.value)
  }
}
