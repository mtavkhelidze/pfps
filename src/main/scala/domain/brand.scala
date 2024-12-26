package ge.zgharbi.pfps
package domain

import ext.http4s.queryParam
import ext.http4s.refined._
import optics.uuid

import derevo.cats._
import derevo.circe.magnolia.{decoder, encoder}
import derevo.derive
import eu.timepit.refined.auto._
import eu.timepit.refined.cats._
import eu.timepit.refined.types.string.NonEmptyString
import io.circe.{Decoder, Encoder}
import io.circe.refined._
import io.estatico.newtype.macros.newtype

import java.util.UUID
import scala.util.control.NoStackTrace

object brand {
  @derive(encoder, decoder, show, uuid, eqv)
  @newtype case class BrandId(value: UUID)

  @derive(decoder, encoder, eqv, show)
  @newtype case class BrandName(value: String) {
    def toBrand(brandId: BrandId): Brand =
      Brand(brandId, this)
  }

  @derive(queryParam, show)
  @newtype case class BrandParam(value: NonEmptyString) {
    def toDomain: BrandName = BrandName(value.toLowerCase.capitalize)
  }

  object BrandParam {

    implicit val jsonEncoder: Encoder[BrandParam] =
      Encoder.forProduct1("name")(_.value)

    implicit val jsonDecoder: Decoder[BrandParam] =
      Decoder.forProduct1("name")(BrandParam.apply)
  }

  @derive(decoder, encoder, eqv, show)
  case class Brand(uuid: BrandId, name: BrandName)

  @derive(decoder, encoder)
  case class InvalidBrand(value: String) extends NoStackTrace
}
