package com.ferran.projects.crypto.analytics.utils

import com.ferran.projects.crypto.analytics.model.CryptoAnalyticModel.SmartBit.{BlockDetails, DetailedBlockRequest, Transaction}

import scala.collection.immutable
import fr.acinq.bitcoin._
import fr.acinq.bitcoin.Crypto._
import scodec.bits.ByteVector

object CryptoAnalyiticUtils {

  implicit class PimpedTransactions(transactions: List[Transaction]) {
    //TODO write a test for this method
    def filterInterestingTransactions(interestingPattern: String): Seq[Transaction] = {
      transactions.filter(transaction =>
        transaction.outputs.exists(outputDetails =>
          outputDetails.addresses.exists(addressNum =>
            addressNum.address contains interestingPattern)))
    }
  }
}
