package ge.zgharbi.pfps
package http.routes.auth

import domain.auth.{InvalidPassword, LoginUser, UserNotFound}
import domain.tokenEncoder
import ext.http4s.refined.RefinedRequestDecoder
import services.Auth

import cats.MonadThrow
import cats.implicits._
import org.http4s.circe.JsonDecoder
import org.http4s.dsl.Http4sDsl
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.server.Router

final case class LoginRoutes[F[_]: JsonDecoder: MonadThrow](auth: Auth[F])
    extends Http4sDsl[F] {
  private[routes] val prefix = "/auth"

  private val httpRoutes: HttpRoutes[F] = HttpRoutes.of[F] {
    case req @ POST -> Root / "login" =>
      req.decodeR[LoginUser] { user =>
        auth
          .login(user.username.toDomain, user.password.toDomain)
          .flatMap(Ok(_))
          .recoverWith { case UserNotFound(_) | InvalidPassword(_) =>
            Forbidden()
          }
      }
  }

  val routes: HttpRoutes[F] = Router(prefix -> httpRoutes)
}
