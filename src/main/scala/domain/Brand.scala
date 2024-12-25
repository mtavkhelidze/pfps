package ge.zgharbi.pfps
package domain

import domain.Brand.{BrandId, BrandName}
import optics.uuid

import derevo._
import derevo.cats._
import derevo.circe.magnolia.{decoder, encoder}
import io.estatico.newtype.macros.newtype

import java.util.UUID

@derive(show, encoder, decoder)
case class Brand(uuid: BrandId, name: BrandName)

object Brand {
  @derive(show, encoder, decoder, uuid)
  @newtype case class BrandId(value: UUID)

  @derive(show, encoder, decoder)
  @newtype case class BrandName(value: String)

}
