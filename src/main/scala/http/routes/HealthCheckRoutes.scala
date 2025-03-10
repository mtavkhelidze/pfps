package ge.zgharbi.pfps
package http.routes

import services.HealthCheck

import cats.Monad
import org.http4s.dsl.Http4sDsl
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.server.Router

final case class HealthRoutes[F[_]: Monad](healthCheck: HealthCheck[F])
    extends Http4sDsl[F] {

  private[routes] val prefix = "/healthcheck"

  private val httpRoutes: HttpRoutes[F] =
    HttpRoutes.of[F] { case GET -> Root => Ok(healthCheck.status) }

  val routes: HttpRoutes[F] = Router(prefix -> httpRoutes)
}
