package ge.zgharbi.pfps
package domain

import derevo.cats.eqv
import derevo.circe.magnolia.encoder
import derevo.derive
import io.circe.Encoder
import io.estatico.newtype.macros.newtype
import monocle.Iso

object healthcheck {
  @derive(eqv)
  sealed trait Status

  @derive(encoder)
  @newtype case class RedisStatus(value: Status)

  @derive(encoder)
  @newtype case class PostgresStatus(value: Status)

  @derive(encoder)
  case class AppStatus(redis: RedisStatus, postgres: PostgresStatus)

  object Status {
    val _Bool: Iso[Status, Boolean] =
      Iso[Status, Boolean] {
        case Alive       => true
        case Unreachable => false
      }(if (_) Alive else Unreachable)

    case object Alive extends Status

    case object Unreachable extends Status

    implicit val jsonEncoder: Encoder[Status] =
      Encoder.forProduct1("status")(_.toString)
  }
}
