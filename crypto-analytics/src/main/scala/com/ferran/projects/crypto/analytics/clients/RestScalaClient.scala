package com.ferran.projects.crypto.analytics.clients

import java.util.concurrent.TimeUnit

import org.http4s.Uri
import com.ferran.projects.crypto.analytics.model.CryptoAnalyticModel.Authorization._
import org.http4s.BasicCredentials
import cats.effect._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.client.blaze._
import io.circe.generic.auto._
import org.http4s.headers.{Accept, Authorization}
import org.http4s.circe.{jsonOf, _}
import cats.effect._
import cats.syntax.all._
import org.http4s.client.Client

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

object RestScalaClient extends App {


  val defaultDuration = FiniteDuration(2, TimeUnit.SECONDS)
  implicit val timer : Timer[IO] = IO.timer(ExecutionContext.global)

  def login(uri: Uri,
            credentials: BasicCredentials,
            retries : Int = 3,
            client: Client[IO]): IO[AuthResponse] = {

    val login = {
      Request[IO](
        method = Method.POST,
        uri = uri,
        headers = Headers(Authorization(credentials), Accept(MediaType.`application/json`))
      ).withBody(UrlForm("grant_type" -> "client_credentials"))
    }
    val tokenRequest = client.expect[AuthResponse](login)(jsonOf[IO, AuthResponse])
    retryWithBackoff(tokenRequest, defaultDuration ,retries)
  }

  def retryWithBackoff[A](ioa: IO[A], initialDelay: FiniteDuration, maxRetries: Int)
                         (implicit timer: Timer[IO]): IO[A] = {

    ioa.handleErrorWith { error =>
      if (maxRetries > 0)
        IO.sleep(initialDelay) *> retryWithBackoff(ioa, initialDelay * 2, maxRetries - 1)
      else
        IO.raiseError(error)
    }
  }

}
