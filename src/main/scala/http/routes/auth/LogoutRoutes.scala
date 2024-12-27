package ge.zgharbi.pfps
package http.routes.auth

import domain.user.ApiUser
import services.Auth

import cats.Monad
import cats.implicits._
import dev.profunktor.auth.AuthHeaders
import org.http4s.{AuthedRoutes, HttpRoutes}
import org.http4s.dsl.Http4sDsl
import org.http4s.server.{AuthMiddleware, Router}

final case class LogoutRoutes[F[_]: Monad](auth: Auth[F]) extends Http4sDsl[F] {

  private[routes] val prefix = "/auth"

  private val httpRoutes: AuthedRoutes[ApiUser, F] =
    AuthedRoutes.of { case ar @ POST -> Root / "logout" as user =>
      AuthHeaders
        .getBearerToken(ar.req)
        .traverse_(auth.logout(_, user.value.name)) *>
        NoContent()
    }

  def routes(authMiddleware: AuthMiddleware[F, ApiUser]): HttpRoutes[F] =
    Router(prefix -> authMiddleware(httpRoutes))
}
