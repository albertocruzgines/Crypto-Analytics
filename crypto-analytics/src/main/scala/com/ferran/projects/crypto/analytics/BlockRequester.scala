package com.ferran.projects.crypto.analytics

import cats.effect.IO
import com.ferran.projects.crypto.analytics.BlockRequester.recentBlocks
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
import org.slf4j.LoggerFactory


object BlockRequester extends App {

  val logger = LoggerFactory.getLogger(this.getClass)

  val credentials = BasicCredentials("", "")
  val httpClient: Client[IO] = Http1Client[IO]().unsafeRunSync
  val urlAuth = "https://auth.shapeshift.io/oauth/token"
  val urlSmartBitGetRecentBlocks = "https://api.smartbit.com.au/v1/blockchain/blocks"
  val urlSmartBitGetBlockDetails = "https://api.smartbit.com.au/v1/blockchain/block/"
  val urlShapeshiftGetStatusOfDepositToAddress = "shapeshift.io/txstat/"


  val shapeshiftLogin: IO[String] = for {
    uri <- IO.fromEither(Uri.fromString(urlAuth))
    authResponse <- RestScalaClient.shapeshiftLogin(uri, credentials, client = httpClient)
  } yield authResponse.access_token.token


  def recentBlocksIO: IO[RecentBlocksResponse] = for {
    uri <- IO.fromEither(Uri.fromString(urlSmartBitGetRecentBlocks))
    recentBlocks <- RestScalaClient.getRequest(uri, client = httpClient)(decoder11)
  } yield recentBlocks

  def filteredBlocksIO(height: Long): IO[DetailedBlockResponse] = for {
    uri <- IO.fromEither(Uri.fromString(urlSmartBitGetBlockDetails))
    uriWithBlockHeight = uri.withPath(s"${height}")
    blockDetails <- RestScalaClient.getRequest(uriWithBlockHeight, client = httpClient)(decoder12)
    blocksFiltered = blockDetails.block.transactions.get.filterInterestingTransactions("E")
  } yield blockDetails


  val interestingTransactions = for {

    _ <- logger.info("Starting the script. ")
    recentBlocks <- recentBlocksIO
    _ <- logger.info(s"Getting  ${recentBlocks.blocks.size} recent blocks")
    filteredBlocks = recentBlocks.blocks.map(block =>
      for {
      filteredBlocks <- filteredBlocksIO(block.height)
    } yield filteredBlocks
    )


     //It´s  not considered the case where a block does not have any transactions
      block.transactions.get.filterInterestingTransactions("E")

    shapeshiftTransactions = filteredTransactions.map(transaction =>
      transaction.outputs.map(_.addresses.map(address =>

        for {
          uriShapeshift <- IO.fromEither(Uri.fromString(urlShapeshiftGetStatusOfDepositToAddress))
          uriShapeshiftWithAddress = uri.withPath(s"${address.address}")
          check <- RestScalaClient.checkShapeshiftAddress(uriShapeshiftWithAddress, client = httpClient)
        } yield check
      ))
    )
  } yield filteredTransactions
  ).map(_.unsafeRunSync())
  ).unsafeRunSync()

  println(recentBlocks.unsafeRunSync())

}
