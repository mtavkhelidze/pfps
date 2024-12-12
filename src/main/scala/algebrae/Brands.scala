package ge.zgharbi.pfps
package algebrae

import io.estatico.newtype.macros.newsubtype

import java.util.UUID

object Brands {
  @newsubtype case class BrandId(value: UUID)

  @newsubtype case class BrandName(value: String)

  case class Brand(uuid: BrandId, name: BrandName)
}

trait Brands[F[_]] {
  import Brands.*

  def findAll: F[List[Brand]]
  def create(name: BrandName): F[BrandId]
}
