package ge.zgharbi.pfps
package domain

import domain.Users.{Password, UserName}

import io.estatico.newtype.macros.newtype

import java.util.UUID

object Auth {
  @newtype case class UserId(value: UUID)

  @newtype case class JwtToken(value: String)

  case class User(id: UserId, name: UserName)
}

trait AuthService[F[_]] {
  import Auth._

  def findUser(token: JwtToken): F[Option[User]]
  def newUser(username: UserName, password: Password): F[JwtToken]
  def login(username: UserName, password: Password): F[JwtToken]
  def logout(token: JwtToken, username: UserName): F[Unit]
}
