package ge.zgharbi.pfps
package domain

import optics.uuid

import derevo.circe.magnolia.{decoder, encoder}
import derevo.derive
import io.estatico.newtype.macros.newtype

import java.util.UUID

object user {
  @derive(encoder, decoder)
  @newtype case class UserName(value: String)

  @derive(encoder, decoder)
  @newtype case class Password(value: String)

  @derive(encoder, decoder)
  @newtype case class EncryptedPassword(value: String)

  @derive(encoder, decoder, uuid)
  @newtype case class UserId(value: UUID)

  @derive(decoder, encoder)
  case class User(id: UserId, name: UserName)

  @derive(decoder, encoder)
  @newtype case class ApiUser(value: User)

//  @derive(decoder, encoder, show)
//  case class UserWithPassword(
//    id: UserId,
//    name: UserName,
//    password: EncryptedPassword,
//  )
}
//
//trait Users[F[_]] {
//  def find(username: UserName): F[Option[UserWithPassword]]
//  def create(username: UserName, password: EncryptedPassword): F[UserId]
//}
