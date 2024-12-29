package ge.zgharbi.pfps
package services

import domain.category.{ Category, CategoryId, CategoryName }
import domain.ID
import effects.GenUUID
import services.codecs.{ categoryId, categoryName }

import cats.effect._
import cats.implicits._
import skunk._
import skunk.implicits._

trait Categories[F[_]] {
  def findAll: F[List[Category]]
  def create(name: CategoryName): F[CategoryId]
}

object Categories {
  def make[F[_]: GenUUID: MonadCancelThrow](
    postgres: Resource[F, Session[F]],
  ): Categories[F] =
    new Categories[F] {
      import CategorySQL._

      def findAll: F[List[Category]] =
        postgres.use(_.execute(selectAll))

      def create(name: CategoryName): F[CategoryId] =
        postgres.use { session =>
          session.prepare(insertCategory).flatMap { cmd =>
            ID.make[F, CategoryId].flatMap { id =>
              cmd.execute(Category(id, name)).as(id)
            }
          }
        }
    }
}
private object CategorySQL {

  val codec: Codec[Category] =
    (categoryId *: categoryName).imap { case i *: n *: _ =>
      Category(i, n)
    }(c => Category.unapply(c).get)

  val selectAll: Query[Void, Category] =
    sql"""
        select * from categories
       """.query(codec)

  val insertCategory: Command[Category] =
    sql"""
        INSERT INTO categories
        VALUES ($codec)
        """.command

}
