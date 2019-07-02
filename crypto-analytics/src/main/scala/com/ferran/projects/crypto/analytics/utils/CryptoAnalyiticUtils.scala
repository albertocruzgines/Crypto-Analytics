package com.ferran.projects.crypto.analytics.utils

import com.ferran.projects.crypto.analytics.model.CryptoAnalyticModel.SmartBit.{BlockDetails, DetailedBlockResponse, Transaction}

object CryptoAnalyiticUtils {

  implicit class PimpedTransactions(transactions: List[Transaction]) {
    //TODO write a test for this method
    def filterInterestingTransactions(interestingPattern: String): Seq[Transaction] = {
      transactions.filter(transaction =>
        transaction.outputs.get.exists(outputDetails =>
          outputDetails.addresses.get.exists(addressNum =>
            addressNum contains interestingPattern)))
    }
  }
}
