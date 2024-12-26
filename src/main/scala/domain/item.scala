package ge.zgharbi.pfps
package domain

import domain.brand._
import domain.category.{Category, CategoryId}
import optics.uuid

import derevo.circe.magnolia._
import derevo.derive
import io.estatico.newtype.macros.newtype
import squants.market._

import java.util.UUID

object item {
  @derive(encoder, decoder, uuid,  keyDecoder, keyEncoder)
  @newtype case class ItemId(value: UUID)

  @derive(encoder, decoder)
  @newtype case class ItemName(value: String)

  @derive(encoder, decoder)
  @newtype case class ItemDescription(value: String)

  @derive(encoder, decoder)
  case class Item(
    uuid: ItemId,
    name: ItemName,
    description: ItemDescription,
    price: Money,
    brand: Brand,
    category: Category,
  )

  @derive(encoder, decoder)
  case class CreateItem(
    name: ItemName,
    description: ItemDescription,
    price: Money,
    brandId: BrandId,
    categoryId: CategoryId,
  )

  @derive(encoder, decoder)
  case class UpdateItem(id: ItemId, price: Money)

}
