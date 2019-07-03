package com.ferran.projects.crypto.analytics.utils

import com.ferran.projects.crypto.analytics.model.CryptoAnalyticModel.Shapeshift.ShapeshiftResponse
import com.ferran.projects.crypto.analytics.model.CryptoAnalyticModel.SmartBit.{BlockDetails, DetailedBlockResponse, Transaction}
import com.sksamuel.elastic4s.http.{ElasticClient, ElasticProperties}
import com.sksamuel.elastic4s.{ElasticsearchClientUri, IndexAndType, Indexable, RefreshPolicy}
import io.circe.syntax._
import io.circe.generic.auto._


object CryptoAnalyiticUtils {

  implicit class PimpedTransactions(transactions: List[Transaction]) {
    def filterInterestingTransactions(interestingPattern: String): Seq[Transaction] = {
      transactions.filter(transaction =>
        transaction.outputs.get.exists(outputDetails =>
          outputDetails.addresses.get.exists(addressNum =>
            addressNum contains interestingPattern)))
    }
  }


  def elasticLoad(shapeshiftResponse: ShapeshiftResponse) = {

    import com.sksamuel.elastic4s.http.ElasticDsl._

    val client = ElasticClient(ElasticProperties("http://localhost:9200"))

    val indexName = "cryptoAnalytics"
    val docType = "shapeshift"

    client.execute {
      createIndex(indexName).mappings(
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
      override def json(s: ShapeshiftResponse): String = shapeshiftResponse.asJson.toString.stripMargin
    }

    client.execute {
      indexInto(indexName / docType).doc(shapeshiftResponse)
    }.await

    client.close()
  }

}
