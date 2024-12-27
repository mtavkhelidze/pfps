package ge.zgharbi.pfps
package http.routes.admin

import domain.item.{CreateItemParam, UpdateItemParam}
import domain.user.AdminUser
import ext.http4s.refined.RefinedRequestDecoder
import services.Items

import cats.MonadThrow
import cats.implicits.{catsSyntaxFlatMapOps, toFlatMapOps}
import io.circe.JsonObject
import io.circe.syntax.EncoderOps
import org.http4s.{AuthedRoutes, HttpRoutes}
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.circe.JsonDecoder
import org.http4s.dsl.Http4sDsl
import org.http4s.server.{AuthMiddleware, Router}

final case class ItemAdminRoutes[F[_]: JsonDecoder: MonadThrow](items: Items[F])
    extends Http4sDsl[F] {
  private[admin] val prefix = "/items"

  private val httpRoutes: AuthedRoutes[AdminUser, F] =
    AuthedRoutes.of {
      // Create new item
      case ar @ POST -> Root as _ =>
        ar.req.decodeR[CreateItemParam] { item =>
          items.create(item.toDomain).flatMap { id =>
            Created(JsonObject.singleton("item_id", id.asJson))
          }
        }
      // Update price of item
      case ar @ PUT -> Root as _ =>
        ar.req.decodeR[UpdateItemParam] { item =>
          items.update(item.toDomain) >> Ok()
        }
    }
  def routes(authMiddleware: AuthMiddleware[F, AdminUser]): HttpRoutes[F] =
    Router(prefix -> authMiddleware(httpRoutes))
}
