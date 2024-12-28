package ge.zgharbi.pfps
package optics

import ext.derevo.Derive

import monocle.Iso

import java.util.UUID

trait IsUUID[A] {
  // @misha: can be id, _id, self, whatever...
  def _UUID: Iso[UUID, A]
}

object IsUUID {
  def apply[A: IsUUID]: IsUUID[A] = implicitly

  implicit val identityUUID: IsUUID[UUID] = new IsUUID[UUID] {
    override def _UUID: Iso[UUID, UUID] = Iso[UUID, UUID](identity)(identity)
  }
}

object uuid extends Derive[IsUUID]
