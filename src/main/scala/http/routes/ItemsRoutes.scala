package ge.zgharbi.pfps
package http.routes

import domain.brand.BrandParam
import ext.http4s.refined.refinedQueryParamDecoder
import services.Items

import cats.Monad
import org.http4s._
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.dsl.Http4sDsl

final case class ItemsRoutes[F[_]: Monad](items: Items[F])
    extends Http4sDsl[F] {
  private[routes] val prefix = "/items"

  private object BrandQueryParam
      extends OptionalQueryParamDecoderMatcher[BrandParam]("brand")

  private val httpRoutes: HttpRoutes[F] = HttpRoutes.of {
    case GET -> Root :? BrandQueryParam(brand) =>
      Ok(brand.fold(items.findAll)(b => items.findBy(b.toDomain)))
  }
}
