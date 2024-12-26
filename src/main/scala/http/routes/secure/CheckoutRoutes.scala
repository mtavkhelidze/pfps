package ge.zgharbi.pfps
package http.routes.secure

import domain.cart.{CartNotFound, EmptyCartError}
import domain.checkout.Card
import domain.order.PaymentError
import domain.user.ApiUser
import ext.http4s.refined.RefinedRequestDecoder
import programs.Checkout

import cats.MonadThrow
import cats.implicits._
import org.http4s.{AuthedRoutes, HttpRoutes}
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.circe.JsonDecoder
import org.http4s.dsl.Http4sDsl
import org.http4s.server.{AuthMiddleware, Router}

final case class CheckoutRoutes[F[_]: JsonDecoder: MonadThrow](
  checkout: Checkout[F],
) extends Http4sDsl[F] {
  private[routes] val prefix = "/checkout"

  private val httpRoutes: AuthedRoutes[ApiUser, F] =
    AuthedRoutes.of { case ar @ POST -> Root as user =>
      ar.req.decodeR[Card] { card =>
        checkout
          .process(user.value.id, card)
          .flatMap(Created(_))
          .recoverWith {
            case EmptyCartError =>
              BadRequest("Shopping cart is empty!")
            case e: PaymentError =>
              BadRequest(e.show)
            case CartNotFound(userId) =>
              NotFound(s"Cart not found for user: ${userId.value}")
          }
      }
    }

  def routes(authMiddleware: AuthMiddleware[F, ApiUser]): HttpRoutes[F] =
    Router(prefix -> authMiddleware(httpRoutes))
}
