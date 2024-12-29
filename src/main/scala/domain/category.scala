package ge.zgharbi.pfps
package domain

import optics.uuid

import derevo.cats.show
import derevo.circe.magnolia.{decoder, encoder}
import derevo.derive
import io.estatico.newtype.macros.newtype

import java.util.UUID

object category {

  @derive(show, encoder, decoder)
  case class Category(uuid: CategoryId, name: CategoryName)

  @derive(show, encoder, decoder, uuid)
  @newtype case class CategoryId(value: UUID)

  @derive(show, encoder, decoder)
  @newtype case class CategoryName(value: String)
}
