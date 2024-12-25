package ge.zgharbi.pfps
package services

import domain.Brand.{BrandId, BrandName}
import domain.Brand

trait Brands[F[_]] {
  def findAll: F[List[Brand]]
  def create(name: BrandName): F[BrandId]
}
