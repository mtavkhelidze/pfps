package ge.zgharbi.pfps
package http.routes.secure

import domain.user.ApiUser
import http.vars.OrderIdVar
import services.Orders

import cats.Monad
import org.http4s.{AuthedRoutes, HttpRoutes}
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.dsl.Http4sDsl
import org.http4s.server.{AuthMiddleware, Router}

final case class OrderRoutes[F[_]: Monad](orders: Orders[F])
    extends Http4sDsl[F] {

  private[routes] val prefix = "/orders"

  private val httpRoutes: AuthedRoutes[ApiUser, F] = AuthedRoutes.of {

    case GET -> Root as user =>
      Ok(orders.findBy(user.value.id))

    case GET -> Root / OrderIdVar(orderId) as user =>
      Ok(orders.get(user.value.id, orderId))

  }

  def routes(authMiddleware: AuthMiddleware[F, ApiUser]): HttpRoutes[F] =
    Router(prefix -> authMiddleware(httpRoutes))
}
