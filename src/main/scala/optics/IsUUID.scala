package ge.zgharbi.pfps
package optics

import ext.derevo.Derive

import monocle.Iso

import java.util.UUID

trait IsUUID[A] {
  def _UUIS: Iso[UUID, A]
}

object IsUUID {
  def apply[A: IsUUID]: IsUUID[A] = implicitly

  implicit val identityUUID: IsUUID[UUID] = new IsUUID[UUID] {
    override def _UUIS: Iso[UUID, UUID] = Iso[UUID, UUID](identity)(identity)
  }
}

object uuid extends Derive[IsUUID]
