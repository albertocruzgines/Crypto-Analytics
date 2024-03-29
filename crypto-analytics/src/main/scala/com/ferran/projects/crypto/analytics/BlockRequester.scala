package com.ferran.projects.crypto.analytics

import cats.effect.IO
import com.ferran.projects.crypto.analytics.clients.RestScalaClient
import com.ferran.projects.crypto.analytics.model.CryptoAnalyticModel.Shapeshift.ShapeshiftResponse
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

  val httpClient: Client[IO] = Http1Client[IO]().unsafeRunSync

  val credentials = BasicCredentials("", "")
  val urlAuth = "https://auth.shapeshift.io/oauth/token"
  val urlSmartBitGetRecentBlocks = "https://api.smartbit.com.au/v1/blockchain/blocks"
  val urlSmartBitGetBlockDetails = "https://api.smartbit.com.au/v1/blockchain/block/"
  val urlShapeshiftGetStatusOfDepositToAddress = "https://shapeshift.io/txstat/"

  // This method is not used in this exercice
  def shapeshiftLogin: IO[String] = for {
    uri <- IO.fromEither(Uri.fromString(urlAuth))
    authResponse <- RestScalaClient.shapeshiftLogin(uri, credentials, client = httpClient)
  } yield authResponse.access_token.token

  // Call to the endpoint to get the recent blocks
  def recentBlocksIO: IO[RecentBlocksResponse] = for {
    uri <- IO.fromEither(Uri.fromString(urlSmartBitGetRecentBlocks))
    recentBlocks <- RestScalaClient.getRequest(uri, client = httpClient)(decoder11)
  } yield recentBlocks

  // For the recent blocks it´s taken its height and it´s made a call to the endpoint to get the details of the block.
  // It will look for the output address and filter just the ones that follow the "interesting pattern" (its base58
  // contains an 'E')
  def filteredBlocksIO(height: Long): IO[Seq[Transaction]] = for {
    uri <- IO.fromEither(Uri.fromString(s"${urlSmartBitGetBlockDetails}${height}"))
    _ = println(s"Uri: ${uri.toString()} ")
    _ = println(s"Processing block with height: ${height} ")
    blockDetails <- RestScalaClient.getRequest(uri, client = httpClient)(decoder12)
    txFiltered = blockDetails.block.transactions.get.filterInterestingTransactions("E")
  } yield txFiltered

  // For those transactions that have at least one address that follows the "interesting pattern", it will be checked in
  // the ShapeShift API if the address belongs to Shapeshit.
  def checkAddressShapeshiftIO(address: String): IO[ShapeshiftResponse] = for {
    uri <- IO.fromEither(Uri.fromString(s"${urlShapeshiftGetStatusOfDepositToAddress}${address}"))
    _ = println(s"Uri: ${uri.toString()} ")
    _ = println(s"Processing block with address: ${address} ")
    checkAddress <- RestScalaClient.getRequest(uri, client = httpClient)(decoder13)
  } yield checkAddress

  // It´s called the previous methods in a monadic way. In case the address belongs to Shapeshift, it´s loaded the data
  // into ElasticSearch
  val shapeshiftResponseIO = for {
    recentBlocks <- recentBlocksIO
    _ = println("Starting the script")
    _ = println(s"Getting  ${recentBlocks.blocks.size} recent blocks")
    interestingTx = recentBlocks.blocks.get.map(block => for {
      filteredTx <- filteredBlocksIO(block.height)
      _ = println(s"Processing block with height: ${block.height} ")
      _ = println(s"Transactions with interesting pattern: ${filteredTx.map(_.txid)} ")

      shapeshitResponse = filteredTx.map(tx =>
        tx.outputs.get.map(txOutput =>
          txOutput.addresses.get
            .map(address => for {
              shapeshiftTx <- checkAddressShapeshiftIO(address)
              _ = if (shapeshiftTx.status.equals("complete")) {elasticLoad(shapeshiftTx)}
              _ = println(s"Shapeshit response: ${shapeshiftTx} ")
            } yield shapeshiftTx
            ).map(_.unsafeRunSync())
        )
      )
    } yield shapeshitResponse
    ).map(_.unsafeRunSync())
  } yield interestingTx

  shapeshiftResponseIO.unsafeRunSync()

}
