package com.ferran.projects.crypto.analytics

import cats.effect.IO
import com.ferran.projects.crypto.analytics.clients.RestScalaClient
import com.ferran.projects.crypto.analytics.model.CryptoAnalyticModel.{Shapeshift, SmartBit}
import com.ferran.projects.crypto.analytics.model.CryptoAnalyticModel.SmartBit._
import com.ferran.projects.crypto.analytics.utils.CryptoAnalyiticUtils._
import org.http4s.client.Client
import org.http4s.client.blaze.Http1Client
import org.http4s.{BasicCredentials, Uri}
import com.ferran.projects.crypto.analytics.utils._
import io.circe.generic.auto._
import io.circe.generic.semiauto._


object BlockRequester extends App {

  val credentials = BasicCredentials("38f08821-e227-4a84-9f4c-cb8c9b5ee46b", "1n1WJUQis58oqh9riUm1gnmuDjRVagEeUTJRcB19yRM7")
  val httpClient: Client[IO] = Http1Client[IO]().unsafeRunSync
  val urlAuth = "https://auth.shapeshift.io/oauth/token"
  val urlSmartBitGetRecentBlocks = "https://api.smartbit.com.au/v1/blockchain/blocks"
  val urlSmartBitGetBlockDetails = "https://api.smartbit.com.au/v1/blockchain/block/"
  val urlShapeshiftGetStatusOfDepositToAddress = "shapeshift.io/txstat/"


  val shapeshiftLogin: IO[String] = for {
    uri <- IO.fromEither(Uri.fromString(urlAuth))
    authResponse <- RestScalaClient.shapeshiftLogin(uri, credentials, client = httpClient)
  } yield authResponse.access_token.token


  val recentBlocks: IO[RecentBlocksResponse] = for {
    uri <- IO.fromEither(Uri.fromString(urlSmartBitGetRecentBlocks))
    recentBlocks <- RestScalaClient.getSmartBitBlockDetails(uri, client = httpClient)(decoder11)
  } yield recentBlocks


//  val interestingTransactions = recentBlocks.map(_.blocks.map(block =>
//
//    for {
//      uri <- IO.fromEither(Uri.fromString(urlSmartBitGetBlockDetails))
//      uriWithBlockHeigh = uri.withPath(s"${block.height}")
//      blockDetails <- RestScalaClient.getSmartBitBlockDetails(uriWithBlockHeigh, client = httpClient)(decoder12)
//      filteredTransactions = blockDetails.asInstanceOf[DetailedBlockResponse].
//        block.transactions.filterInterestingTransactions("E")
//
//      shapeshiftTransactions = filteredTransactions.map(transaction =>
//        transaction.outputs.map(_.addresses.map(address =>
//
//          for {
//            uriShapeshift <- IO.fromEither(Uri.fromString(urlShapeshiftGetStatusOfDepositToAddress))
//            uriShapeshiftWithAddress = uri.withPath(s"${address.address}")
//            check <- RestScalaClient.checkShapeshiftAddress(uriShapeshiftWithAddress, client = httpClient)
//          } yield check
//
//        ))
//      )
//    } yield filteredTransactions
//  ).map(_.unsafeRunSync())
//  ).unsafeRunSync()

  println(recentBlocks.unsafeRunSync())

}
