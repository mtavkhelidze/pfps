package ge.zgharbi.pfps
package domain

import effects.GenUUID
import optics.IsUUID

import cats.Functor
import cats.implicits.toFunctorOps

object ID {
  def make[F[_]: Functor: GenUUID, A: IsUUID]: F[A] =
    GenUUID[F].make.map(IsUUID[A]._UUID.get)

  def read[F[_]: Functor: GenUUID, A: IsUUID](s: String): F[A] =
    GenUUID[F].read(s).map(IsUUID[A]._UUID.get)
}
