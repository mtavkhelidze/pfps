package ge.zgharbi.pfps
package services

import domain.category.{Category, CategoryId, CategoryName}

trait Categories[F[_]] {
  def findAll: F[List[Category]]
  def create(name: CategoryName): F[CategoryId]
}
