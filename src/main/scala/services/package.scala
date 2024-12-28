package ge.zgharbi.pfps

import domain.brand.{BrandId, BrandName}

import skunk._
import skunk.codec.all._

package object services {
  object codecs {
    val brandId: Codec[BrandId] = uuid.imap(BrandId.apply)(_.value)
    val brandName: Codec[BrandName] = varchar.imap(BrandName.apply)(_.value)
  }
}
