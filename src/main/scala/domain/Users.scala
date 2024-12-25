package ge.zgharbi.pfps
package domain

import domain.Auth.UserId
import domain.Users.{EncryptedPassword, UserName}

import io.estatico.newtype.macros.newtype

object Users {
  @newtype case class UserName(value: String)
  @newtype case class Password(value: String)
  @newtype case class EncryptedPassword(value: String)
}

case class UserWithPassword(
  id: UserId,
  name: UserName,
  password: EncryptedPassword,
)

trait Users[F[_]] {
  def find(username: UserName): F[Option[UserWithPassword]]
  def create(username: UserName, password: EncryptedPassword): F[UserId]
}
