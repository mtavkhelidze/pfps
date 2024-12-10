package ge.zgharbi.books
package ch01

import cats.Functor
import cats.effect.*
import cats.effect.kernel.Ref
import cats.syntax.all.*

trait Counter[F[_]] {
  def inc: F[Unit]
  def get: F[Int]
}

object Counter {
  def make[F[_]: Functor: Ref.Make]: F[Counter[F]] = {
    Ref.of[F, Int](0).map { ref =>
      new Counter[F] {
        override def inc: F[Unit] = ref.update(_ + 1)
        override def get: F[Int] = ref.get
      }
    }
  }
}

object Main extends IOApp.Simple {
  def run: IO[Unit] = Counter.make[IO].flatMap { c =>
    for {
      _ <- c.get.flatMap(IO.println)
      _ <- c.inc
      _ <- c.get.flatMap(IO.println)
      _ <- c.inc.replicateA(5).void
      _ <- c.get.flatMap(IO.println)
    } yield ()
  }
}
