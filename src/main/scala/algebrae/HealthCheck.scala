package ge.zgharbi.pfps
package algebrae

import derevo.circe.magnolia.encoder
import derevo.derive
import io.circe.Encoder
import io.estatico.newtype.macros.newtype
import monocle.Iso

object HealthCheck {
  @derive(encoder)
  @newtype
  case class RedisStatus(value: Status)

  @derive(encoder)
  @newtype
  case class PostgresStatus(value: Status)

  @derive(encoder)
  case class AppStatus(redis: RedisStatus, postgres: PostgresStatus)
}

sealed trait Status
object Status {
  case object Alive extends Status
  case object Unreachable extends Status

  val _Bool: Iso[Status, Boolean] =
    Iso[Status, Boolean] {
      case Alive       => true
      case Unreachable => false
    }(if (_) Alive else Unreachable)

  implicit val jsonEncoder: Encoder[Status] =
    Encoder.forProduct1("status")(_.toString)
}

trait HealthCheck[F[_]] {
  import HealthCheck.*

  def check: F[AppStatus]
}
