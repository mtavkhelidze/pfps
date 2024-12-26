package ge.zgharbi.pfps
package domain

import io.estatico.newtype.macros.newtype

import java.util.UUID

object user {
  @newtype case class UserName(value: String)

  @newtype case class Password(value: String)

  @newtype case class EncryptedPassword(value: String)

  @newtype case class UserId(value: UUID)

  case class User(id: UserId, name: UserName)

  case class UserWithPassword(
    id: UserId,
    name: UserName,
    password: EncryptedPassword,
  )
}
//
//trait Users[F[_]] {
//  def find(username: UserName): F[Option[UserWithPassword]]
//  def create(username: UserName, password: EncryptedPassword): F[UserId]
//}
