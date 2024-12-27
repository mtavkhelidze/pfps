package ge.zgharbi.pfps
package domain

import domain.brand._
import domain.category.{Category, CategoryId}
import optics.uuid

import derevo.cats.show
import derevo.circe.magnolia._
import derevo.derive
import eu.timepit.refined.api.Refined
import eu.timepit.refined.string.{Uuid, ValidBigDecimal}
import eu.timepit.refined.types.string.NonEmptyString
import io.circe.refined._
import io.estatico.newtype.macros.newtype
import squants.market._

import java.util.UUID

object item {
  @derive(decoder, encoder, show)
  @newtype case class ItemNameParam(value: NonEmptyString)

  @derive(decoder, encoder, show)
  @newtype case class ItemDescriptionParam(value: NonEmptyString)

  @derive(decoder, encoder)
  @newtype case class PriceParam(value: String Refined ValidBigDecimal)

  @derive(encoder, decoder, uuid, keyDecoder, keyEncoder, show)
  @newtype case class ItemId(value: UUID)

  @derive(encoder, decoder, show)
  @newtype case class ItemName(value: NonEmptyString)

  @derive(encoder, decoder, show)
  @newtype case class ItemDescription(value: NonEmptyString)

  @derive(encoder, decoder, show)
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

  @derive(decoder, encoder)
  @newtype case class ItemIdParam(value: String Refined Uuid)

  @derive(decoder, encoder)
  case class UpdateItemParam(id: ItemIdParam, price: PriceParam) {
    def toDomain: UpdateItem =
      UpdateItem(
        ItemId(UUID.fromString(id.value.value)),
        USD(BigDecimal(price.value.toString())),
      )
  }

  @derive(decoder, encoder)
  case class CreateItemParam(
    name: ItemNameParam,
    description: ItemDescriptionParam,
    price: PriceParam,
    brandId: BrandId,
    categoryId: CategoryId,
  ) {
    def toDomain: CreateItem =
      CreateItem(
        ItemName(name.value),
        ItemDescription(description.value),
        USD(BigDecimal(price.value.toString())),
        brandId,
        categoryId,
      )
  }
}
