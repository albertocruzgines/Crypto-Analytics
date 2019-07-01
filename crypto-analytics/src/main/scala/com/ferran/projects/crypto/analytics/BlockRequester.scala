package com.ferran.projects.crypto.analytics

import com.ferran.projects.crypto.analytics.clients.RestScalaClient.login
import cats.effect.IO
import com.ferran.projects.crypto.analytics.clients.RestScalaClient
import org.http4s.client.Client
import org.http4s.client.blaze.Http1Client
import org.http4s.{BasicCredentials, Uri}

object BlockRequester {


  val credentials = BasicCredentials("38f08821-e227-4a84-9f4c-cb8c9b5ee46b", "1n1WJUQis58oqh9riUm1gnmuDjRVagEeUTJRcB19yRM7")

  val httpClient: Client[IO] = Http1Client[IO]().unsafeRunSync

  val urlAuth = "https://auth.shapeshift.io/oauth/token"

  val login =  for {
    uri <- IO.fromEither(Uri.fromString(urlAuth))
    authResponse <- RestScalaClient.login(uri, credentials, client = httpClient)
  } yield authResponse.access_token



}
