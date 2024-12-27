package ge.zgharbi.pfps
package services

import domain.user.{Password, User, UserName}

import dev.profunktor.auth.jwt.JwtToken

trait Auth[F[_]] {

  def findUser(token: JwtToken): F[Option[User]]

  def newUser(username: UserName, password: Password): F[JwtToken]

  def login(username: UserName, password: Password): F[JwtToken]

  def logout(token: JwtToken, username: UserName): F[Unit]
}
