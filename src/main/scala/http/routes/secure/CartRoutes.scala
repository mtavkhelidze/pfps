package ge.zgharbi.pfps
package http.routes.secure

import domain.cart.Cart
import domain.user.ApiUser
import http.vars.ItemIdVar
import services.ShoppingCart

import cats.implicits._
import cats.Monad
import org.http4s.dsl.Http4sDsl
import org.http4s.AuthedRoutes
import org.http4s.circe._
import org.http4s.circe.CirceEntityEncoder._

final case class CartRoutes[F[_]: JsonDecoder: Monad](
  shoppingCart: ShoppingCart[F],
) extends Http4sDsl[F] {
  private[routes] val prefix = "/cart"

  private val httpRoutes: AuthedRoutes[ApiUser, F] = AuthedRoutes.of {
    case GET -> Root as user =>
      Ok(shoppingCart.get(user.value.id))

    case ar @ POST -> Root as user =>
      ar.req.asJsonDecode[Cart].flatMap {
        _.items
          .map { case (id, quantity) =>
            shoppingCart.add(user.value.id, id, quantity)
          }
          .toList
          .sequence *> Created()
      }

    case ar @ PUT -> Root as user =>
      ar.req.asJsonDecode[Cart].flatMap { cart =>
        shoppingCart.update(user.value.id, cart) *> Ok()
      }

    case DELETE -> Root / ItemIdVar(itemId) as user =>
      shoppingCart.removeItem(user.value.id, itemId) *>
        NoContent()
  }
}
