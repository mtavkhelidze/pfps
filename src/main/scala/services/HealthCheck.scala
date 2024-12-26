package ge.zgharbi.pfps
package services

import domain.healthcheck._

trait HealthCheck[F[_]] {

  def status: F[AppStatus]
}
