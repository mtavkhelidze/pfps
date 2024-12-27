package ge.zgharbi.pfps
package domain

import optics.uuid

import derevo.cats.show
import derevo.circe.magnolia.{decoder, encoder}
import derevo.derive
import eu.timepit.refined.auto._
import io.estatico.newtype.macros.newtype

import java.util.UUID

object user {
  @derive(encoder, decoder, show)
  @newtype case class UserName(value: String)

  @derive(encoder, decoder)
  @newtype case class Password(value: String)

  @derive(encoder, decoder)
  @newtype case class EncryptedPassword(value: String)

  @derive(encoder, decoder, uuid, show)
  @newtype case class UserId(value: UUID)

  @derive(decoder, encoder, show)
  case class User(id: UserId, name: UserName)

  @derive(show)
  @newtype case class ApiUser(value: User)

  @derive(show)
  @newtype
  case class AdminUser(value: User)
}
