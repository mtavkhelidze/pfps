package ge.zgharbi.pfps
package http.routes

import services.Brands

import cats.Monad
import org.http4s.dsl.Http4sDsl
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.server.Router

final case class BrandsRoutes[F[_]: Monad](brands: Brands[F])
    extends Http4sDsl[F] {

  private[routes] val prefix = "/brands"
  private val httpRtoues: HttpRoutes[F] = HttpRoutes.of[F] { case GET -> Root =>
    Ok(brands.findAll)
  }
  val routes: HttpRoutes[F] = Router(prefix -> httpRtoues)
}
