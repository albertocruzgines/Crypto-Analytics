package com.ferran.projects.crypto.analytics

import com.ferran.projects.crypto.analytics.clients.RestScalaClient._
import cats.effect.IO
import com.ferran.projects.crypto.analytics.clients.RestScalaClient
import com.ferran.projects.crypto.analytics.model.CryptoAnalyticModel.{Authorization, SmartBit}
import com.ferran.projects.crypto.analytics.model.CryptoAnalyticModel.SmartBit.RecentBlocksRequest
import com.ferran.projects.crypto.analytics.utils.CryptoAnalyiticUtils._
import org.http4s.client.Client
import org.http4s.client.blaze.Http1Client
import org.http4s.{BasicCredentials, Uri}

object BlockRequester extends App {

  val credentials = BasicCredentials("38f08821-e227-4a84-9f4c-cb8c9b5ee46b", "1n1WJUQis58oqh9riUm1gnmuDjRVagEeUTJRcB19yRM7")
  val httpClient: Client[IO] = Http1Client[IO]().unsafeRunSync
  val urlAuth = "https://auth.shapeshift.io/oauth/token"

  val shapeshiftLogin: IO[String] = for {
    uri <- IO.fromEither(Uri.fromString(urlAuth))
    authResponse <- RestScalaClient.shapeshiftLogin(uri, credentials, client = httpClient)
  } yield authResponse.access_token.token


  val urlGetRecentBlocks = "https://api.smartbit.com.au/v1/blockchain/blocks"

  val recentBlocks: IO[RecentBlocksRequest] = for {
    uri <- IO.fromEither(Uri.fromString(urlGetRecentBlocks))
    recentBlocks <- RestScalaClient.getSmartBitRecentBlocks(uri, client = httpClient)
  } yield recentBlocks

  val urlGetBlockDetails = "https://api.smartbit.com.au/v1/blockchain/block/"

  val interestingTransactions = recentBlocks.map(_.blocks.map(block =>
     for {
      uri <- IO.fromEither(Uri.fromString(urlGetBlockDetails))
      uriWithBlockHeigh = uri.withPath(s"${block.height}")
      blockDetails: SmartBit.SmartBitRequest <- RestScalaClient.getSmartBitBlockDetails(uriWithBlockHeigh, client = httpClient)
      //TODO add a note saying that each block should have at least one transaction. If it´s not the case, we want the code to fail.
      filteredTransactions = blockDetails.block.transactions.get.filterInterestingTransactions("E")
    } yield filteredTransactions
  )
  )

  println(recentBlocks.unsafeRunSync())


}
