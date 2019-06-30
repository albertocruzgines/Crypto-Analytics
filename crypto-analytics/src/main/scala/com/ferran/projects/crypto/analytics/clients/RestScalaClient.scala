package com.ferran.projects.crypto.analytics.clients

import org.http4s.Uri
import com.ferran.projects.crypto.analytics.model.CryptoAnalyticModel._
import org.http4s._
import cats.effect._
import org.http4s._, org.http4s.dsl.io._, org.http4s.implicits._

object RestScalaClient extends App {

  val login  = {
    Request[IO](Method.POST,
      uri("https://auth.shapeshift.io/oauth/token"),
      headers = Headers(Header("Authorization", "Basic MzhmMDg4MjEtZTIyNy00YTg0LTlmNGMtY2I4YzliNWVlNDZiOjFuMVdKVVFpczU4b3FoOXJpVW0xZ25tdURqUlZhZ0VlVVRKUmNCMTl5Uk03")))
  }

  val service = HttpService[IO] {
    case _ =>
      IO(Response(Status.Ok))
  }

  val io = service.run(login)

  val response = io.getOrElse().unsafeRunSync()
  println(s"My response is: ${response}")

}
