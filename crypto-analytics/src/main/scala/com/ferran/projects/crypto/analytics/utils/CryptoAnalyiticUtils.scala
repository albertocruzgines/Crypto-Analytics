package com.ferran.projects.crypto.analytics.utils

import com.ferran.projects.crypto.analytics.model.CryptoAnalyticModel.Shapeshift.ShapeshiftResponse
import com.ferran.projects.crypto.analytics.model.CryptoAnalyticModel.SmartBit.{BlockDetails, DetailedBlockResponse, Transaction}
import com.sksamuel.elastic4s.http.{ElasticClient, ElasticProperties}
import com.sksamuel.elastic4s.http.Response
import com.sksamuel.elastic4s.{ElasticsearchClientUri, IndexAndType, Indexable, RefreshPolicy}
import com.sksamuel.elastic4s.embedded.LocalNode
import com.sksamuel.elastic4s.http.search.SearchResponse
import com.sksamuel.elastic4s.http.{RequestFailure, RequestSuccess}
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy
import org.elasticsearch.common.settings.Settings

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


  def elasticLoader(shapeshiftResponse: ShapeshiftResponse) = {

    import com.sksamuel.elastic4s.http.ElasticDsl._

    val client = ElasticClient(ElasticProperties("http://localhost:9200"))

    client.execute {
      createIndex("CryptoAnalytics").mappings(
        mapping("status") as textField("status"),
        mapping("address") as textField("address"),
        mapping("error") as textField("error"),
        mapping("withdraw") as textField("withdraw"),
        mapping("incomingCoin") as doubleField("incomingCoin"),
        mapping("incomingType") as textField("incomingType"),
        mapping("transaction") as textField("transaction"),
        mapping("transactionURL") as textField("transactionURL")
      )
    }.await

    implicit object CharacterIndexable extends Indexable[ShapeshiftResponse] {
      override def json(s: ShapeshiftResponse): String =
        s""" { "status" : "${s.status}", "address" : "${s.address}", "withdraw" : "${s.withdraw}", "error" : "${s.error}",
           | "incomingCoin" : "${s.incomingCoin}", "incomingType" : "${s.incomingType}", "transaction" : "${s.transaction}",
           |  "transactionURL" : "${s.transactionURL}"} """.stripMargin
    }

    client.execute {
      indexInto("CryptoAnalytics" / "Shapeshift").doc(shapeshiftResponse)
    }.await

    client.close()
  }
}
