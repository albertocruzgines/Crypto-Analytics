package com.ferran.projects.crypto.analytics.model

import com.ferran.projects.crypto.analytics.model.CryptoAnalyticModel.SmartBit._
import io.circe.syntax._
import com.ferran.projects.crypto.analytics.utils._
import org.scalatest.{FlatSpec, MustMatchers}
import TestData._
import io.circe.generic.auto._

class CryptoAnalyticalModelTest extends FlatSpec with MustMatchers {

  val jsonEncodedDetailedBlock = detailedBlock.asJson

  "Encoding and decoding DetailedBlock case class" should "return the same value" in {

    val block = jsonEncodedDetailedBlock.hcursor.downField("block").as[BlockDetails]

    val blockDecoded = block match {
      case Right(b) => b
      case Left(exception) => throw exception
    }
    assert(blockDecoded == detailedBlock.block)
  }

  "Encoding and decoding Transaction case class" should "return the same value" in {


    val txs = jsonEncodedDetailedBlock.hcursor.downField("block").downField("transactions").as[List[Transaction]]

    val txsDecoded = txs match {
      case Right(tx) => tx
      case Left(exception) => throw exception
    }
    assert(txsDecoded == detailedBlock.block.transactions)
  }
}



