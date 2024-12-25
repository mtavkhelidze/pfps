package ge.zgharbi.pfps
package services

import domain.{CreateItem, Item, UpdateItem}
import domain.Brand.BrandName
import domain.Item.ItemId

trait Items[F[_]] {
  def findAll: F[List[Item]]
  def findBy(brand: BrandName): F[List[Item]]
  def findById(itemId: ItemId): F[Option[Item]]
  def create(item: CreateItem): F[ItemId]
  def update(item: UpdateItem): F[Unit]
}
