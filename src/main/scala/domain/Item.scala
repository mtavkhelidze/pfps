package ge.zgharbi.pfps
package domain

import domain.Brand._
import domain.Category.CategoryId
import domain.Item.{ItemDescription, ItemId, ItemName}
import optics.uuid

import derevo.cats._
import derevo.circe.magnolia._
import derevo.derive
import io.estatico.newtype.macros.newtype
import squants.market._

import java.util.UUID

object Item {
  @derive(show, encoder, decoder, uuid)
  @newtype case class ItemId(value: UUID)

  @derive(show, encoder, decoder)
  @newtype case class ItemName(value: String)

  @derive(show, encoder, decoder)
  @newtype case class ItemDescription(value: String)
}

@derive(show, encoder, decoder)
case class Item(
  uuid: ItemId,
  name: ItemName,
  description: ItemDescription,
  price: Money,
  brand: Brand,
  category: Category,
)

@derive(show, encoder, decoder)
case class CreateItem(
  name: ItemName,
  description: ItemDescription,
  price: Money,
  brandId: BrandId,
  categoryId: CategoryId,
)

@derive(show, encoder, decoder)
case class UpdateItem(id: ItemId, price: Money)
