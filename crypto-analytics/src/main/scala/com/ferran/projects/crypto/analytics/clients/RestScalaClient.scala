package com.ferran.projects.crypto.analytics.clients

import java.util.concurrent.TimeUnit

import org.http4s.Uri
import org.http4s.BasicCredentials
import org.http4s._
import io.circe.generic.auto._
import org.http4s.headers.{Accept, Authorization}
import org.http4s.circe.{jsonOf, _}
import cats.effect._
import cats.syntax.all._
import com.ferran.projects.crypto.analytics.model.CryptoAnalyticModel.Shapeshift.{AuthResponse, ShapeshiftResponse}
import com.ferran.projects.crypto.analytics.model.CryptoAnalyticModel.SmartBit.Transaction
import org.http4s.client.Client

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

object RestScalaClient {

  val defaultDuration = FiniteDuration(2, TimeUnit.SECONDS)
  implicit val timer: Timer[IO] = IO.timer(ExecutionContext.global)

  def shapeshiftLogin(uri: Uri,
                      credentials: BasicCredentials,
                      retries: Int = 3,
                      client: Client[IO]): IO[AuthResponse] = {

    val loginRequest = {
      Request[IO](
        method = Method.POST,
        uri = uri,
        headers = Headers(Authorization(credentials), Accept(MediaType.`application/json`))
      ).withBody(UrlForm("grant_type" -> "client_credentials"))
    }
    val token = client.expect[AuthResponse](loginRequest)(jsonOf[IO, AuthResponse])
    retryWithBackoff(token, defaultDuration, retries)
  }

 //TODO Remove this method by getRequest
  def checkShapeshiftAddress(uri: Uri,
                             retries: Int = 3,
                             client: Client[IO]): IO[ShapeshiftResponse] = {

    val checkShapeshiftAddress = {
      Request[IO](
        method = Method.GET,
        uri = uri,
        headers = Headers(Accept(MediaType.`application/json`))
      )
    }
    val detailedBlock = client.expect[ShapeshiftResponse](checkShapeshiftAddress)(jsonOf[IO, ShapeshiftResponse])
    retryWithBackoff(detailedBlock, defaultDuration, retries)
  }


  def getRequest[T: io.circe.Decoder](uri: Uri,
                              retries: Int = 3,
                              client: Client[IO]): IO[T] = {

    val getBlockDetails = {
      Request[IO](
        method = Method.GET,
        uri = uri,
        headers = Headers(Accept(MediaType.`application/json`))
      )
    }
    val detailedBlock = client.expect[T](getBlockDetails)(jsonOf[IO, T])
    retryWithBackoff(detailedBlock, defaultDuration, retries)
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
