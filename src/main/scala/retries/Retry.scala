package ge.zgharbi.pfps
package retries

import cats.effect.Temporal
import cats.syntax.show._
import org.typelevel.log4cats.Logger
import retry.{retryingOnAllErrors, RetryDetails, RetryPolicy}
import retry.RetryDetails.{GivingUp, WillDelayAndRetry}

import scala.annotation.unused

trait Retry[F[_]] {
  def retry[A](policy: RetryPolicy[F], retriable: Retriable)(fa: F[A]): F[A]
}

object Retry {
  def apply[F[_]: Retry]: Retry[F] = implicitly

  implicit def forLoggerTemporal[F[_]: Logger: Temporal]: Retry[F] =
    new Retry[F] {
      override def retry[A](policy: RetryPolicy[F], retriable: Retriable)(
        fa: F[A],
      ): F[A] = {
        def onError(@unused e: Throwable, details: RetryDetails): F[Unit] =
          details match {
            case GivingUp(totalRetries, _) =>
              Logger[F].error(
                s"Failed on ${retriable.show} after ${totalRetries} tries.",
              )
            case WillDelayAndRetry(_, retriesSoFar, _) =>
              Logger[F].error(
                s"Failed on ${retriable.show}. We retried $retriesSoFar times.",
              )
          }
        retryingOnAllErrors(policy, onError)(fa)
      }
    }
}
