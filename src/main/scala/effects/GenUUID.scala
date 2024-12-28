package ge.zgharbi.pfps
package effects

import cats.effect.Sync
import cats.ApplicativeThrow

import java.util.UUID

trait GenUUID[F[_]] {
  def make: F[UUID]
  def read(s: String): F[UUID]
}

object GenUUID {
  def apply[F[_]: GenUUID]: GenUUID[F] = implicitly

  implicit def forSync[F[_]: Sync]: GenUUID[F] =
    new GenUUID[F] {
      override def make: F[UUID] = Sync[F].delay(UUID.randomUUID())
      override def read(s: String): F[UUID] =
        ApplicativeThrow[F].catchNonFatal(UUID.fromString(s))
    }
}
