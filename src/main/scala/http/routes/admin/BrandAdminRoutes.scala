package ge.zgharbi.pfps
package http.routes.admin

import domain.brand.BrandParam
import domain.user.AdminUser
import ext.http4s.refined.RefinedRequestDecoder
import services.Brands

import cats.MonadThrow
import cats.implicits.toFlatMapOps
import io.circe.JsonObject
import io.circe.syntax.EncoderOps
import org.http4s.{AuthedRoutes, HttpRoutes}
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.circe.JsonDecoder
import org.http4s.dsl.Http4sDsl
import org.http4s.server.{AuthMiddleware, Router}

final case class AdminBrandRoutes[F[_]: JsonDecoder: MonadThrow](
  brands: Brands[F],
) extends Http4sDsl[F] {
  private[admin] val prefix = "/brands"

  private val httpRoutes: AuthedRoutes[AdminUser, F] =
    AuthedRoutes.of { case ar @ POST -> Root as _ =>
      ar.req.decodeR[BrandParam] { bp =>
        brands.create(bp.toDomain).flatMap { id =>
          Created(JsonObject.singleton("brand_id", id.asJson))
        }
      }
    }

  def routes(authMiddleware: AuthMiddleware[F, AdminUser]): HttpRoutes[F] =
    Router(prefix -> authMiddleware(httpRoutes))
}
