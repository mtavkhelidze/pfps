package ge.zgharbi.pfps
package domain

import io.estatico.newtype.macros.newtype

object auth {
  @newtype case class JwtToken(value: String)
}

//
//trait AuthService[F[_]] {
//
//  def findUser(token: JwtToken): F[Option[User]]
//  def newUser(username: UserName, password: Password): F[JwtToken]
//  def login(username: UserName, password: Password): F[JwtToken]
//  def logout(token: JwtToken, username: UserName): F[Unit]
//}
