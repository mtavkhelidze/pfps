package ge.zgharbi.pfps
package domain

import domain.user.{Password, UserName}

import derevo.cats.show
import derevo.circe.magnolia.{decoder, encoder}
import derevo.derive
import eu.timepit.refined.auto._
import eu.timepit.refined.types.string.NonEmptyString
import io.circe.refined._
import io.estatico.newtype.macros.newtype

import scala.util.control.NoStackTrace

object auth {
  @derive(decoder, encoder, show)
  case class CreateUser(username: UserNameParam, password: PasswordParam)

  @derive(decoder, encoder, show)
  @newtype
  case class UserNameParam(value: NonEmptyString) {
    def toDomain: UserName = UserName(value.toLowerCase)
  }

  @derive(decoder, encoder, show)
  @newtype
  case class PasswordParam(value: NonEmptyString) {
    def toDomain: Password = Password(value)
  }
  @derive(decoder, encoder, show)
  case class LoginUser(username: UserNameParam, password: PasswordParam)

  case class UserNotFound(username: UserName) extends NoStackTrace
  case class UserNameInUse(username: UserName) extends NoStackTrace
  case class InvalidPassword(username: UserName) extends NoStackTrace
  case object UnsupportedOperation extends NoStackTrace

}
