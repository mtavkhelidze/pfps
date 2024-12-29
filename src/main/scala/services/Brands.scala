package ge.zgharbi.pfps
package services

import domain.brand._
import domain.ID
import effects.GenUUID
import services.codecs._

import cats.effect._
import cats.implicits._
import skunk._
import skunk.implicits._

trait Brands[F[_]] {
  def findAll: F[List[Brand]]
  def create(name: BrandName): F[BrandId]
}

object Brands {
  import BrandSQL._

  def make[F[_]: GenUUID: MonadCancelThrow](
    postgres: Resource[F, Session[F]],
  ): Brands[F] = new Brands[F] {

    override def findAll: F[List[Brand]] =
      postgres.use(_.execute(selectAll))

    override def create(name: BrandName): F[BrandId] =
      postgres.use { session =>
        session.prepare(insert).flatMap { cmd =>
          ID.make[F, BrandId].flatMap { id =>
            cmd.execute(Brand(id, name)).as(id)
          }
        }
      }
  }
}

private object BrandSQL {
  val codec: Codec[Brand] =
    (brandId *: brandName).imap { case i *: n *: _ =>
      Brand(i, n)
    }(b => (b.uuid, b.name))

  val selectAll: Query[Void, Brand] =
    sql"select * from brands".query(codec)

  val insert: Command[Brand] =
    sql"""
      insert into brands (uuid, name)
      values ($codec)
    """.command
}
