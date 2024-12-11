package ge.zgharbi.books
package ch01

import cats.effect.*
import cats.effect.std.{Semaphore, Supervisor}

import scala.concurrent.duration.*

object Regions extends IOApp.Simple {
  def run: IO[Unit] = Supervisor[IO].use { s =>
    Semaphore[IO](1).flatMap { sem =>
      s.supervise(p1(sem).foreverM).void *>
        s.supervise(p2(sem).foreverM).void *>
        IO.sleep(5.seconds).void
    }
  }

  private def randomSleep: IO[Unit] =
    IO(scala.util.Random.nextInt(100)).flatMap { ms =>
      IO.sleep((ms + 700).milliseconds)
    }

  private def p1(sem: Semaphore[IO]): IO[Unit] =
    sem.permit.surround(IO.println("Running P1")) *>
      randomSleep

  private def p2(sem: Semaphore[IO]): IO[Unit] =
    sem.permit.surround(IO.println("Running P2")) *>
      randomSleep

}
