package ge.zgharbi.pfps
package optics

import ext.derevo.shop.ext.derevo.Derive

import monocle.Iso

import java.util.UUID

trait IsUUID[A] {
  val _UUIS: Iso[UUID, A]
}

object IsUUID {
  def apply[A: IsUUID]: IsUUID[A] = implicitly

  implicit val identityUUID: IsUUID[UUID] = new IsUUID[UUID] {
    override val _UUIS: Iso[UUID, UUID] = Iso[UUID, UUID](identity)(identity)
  }
}

object uuid extends Derive[IsUUID]
