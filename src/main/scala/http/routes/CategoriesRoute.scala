package ge.zgharbi.pfps
package http.routes

import services.Categories

import cats.Monad
import org.http4s.dsl.Http4sDsl
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.server.Router

final case class CategoriesRoute[F[_]: Monad](categories: Categories[F])
    extends Http4sDsl[F] {

  private[routes] val prefix = "/categories"

  private val httpRtoues: HttpRoutes[F] = HttpRoutes.of { case GET -> Root =>
    Ok(categories.findAll)
  }

  val routes: HttpRoutes[F] = Router(prefix -> httpRtoues)
}
