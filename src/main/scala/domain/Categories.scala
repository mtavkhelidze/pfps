package ge.zgharbi.pfps
package domain

import io.estatico.newtype.macros.newsubtype

import java.util.UUID

object Categories {
  @newsubtype case class CategoryId(value: UUID)
  @newsubtype case class CategoryName(value: String)
  case class Category(uuid: CategoryId, name: CategoryName)
}

trait Categories[F[_]] {
  import Categories._

  def findAll: F[List[Category]]
  def create(name: CategoryName): F[CategoryId]
}
