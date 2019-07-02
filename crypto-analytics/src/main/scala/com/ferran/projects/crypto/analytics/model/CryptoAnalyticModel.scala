package com.ferran.projects.crypto.analytics.model

object CryptoAnalyticModel {

  object Shapeshift {

    case class AccessToken(createdAt: String,
                           updatedAt: String,
                           id: String,
                           clientId: String,
                           expiresAt: String,
                           token: String,
                           userId: String = "",
                           deletedAt: String = "")

    case class AuthResponse(access_token: AccessToken,
                            token_type: String)

    case class ShapeshiftResponse(status: String,
                                  address: String,
                                  withdraw: Option[String],
                                  error: Option[String],
                                  incomingCoin: Option[Double],
                                  incomingType: Option[String],
                                  transaction: Option[String],
                                  transactionURL: Option[String])
  }

  object SmartBit {

    case class Paging(valid_sort: List[String],
                      limit: Int,
                      sort: String,
                      dir: String,
                      prev: Option[String] = None,
                      next: String,
                      prev_link: Option[String] = None,
                      next_link: String = "")

    def emptyPaging = Paging(List.empty, 0 ,"" ,"" , None ,"", None, "")

    case class Pool(name: Option[String] = None,
                    link: Option[String] = None)

    case class Address(num: Array[Byte])

    case class Sign(asm: String,
                    hex: String)


    case class InOrOutDetails(addresses: Option[List[String]] = None,
                              value: Option[String] = None,
                              value_int: Long = 0,
                              txid: Option[String] = None,
                              vout: Option[Int] = None,
                              script_sig: Option[Sign] = None,
                              script_pub_key: Option[Sign] = None,
                              req_sigs: Option[Int] = None,
                              `type`: Option[String] = None,
                              witness: Option[List[String]] = None,
                              sequence: Option[Long] = None,
                              spend_txid: Option[String] = None
                             )

    case class Transaction(txid: Option[String] = None,
                           hash: Option[String] = None,
                           block: Option[Long] = None,
                           confirmations: Option[Long] = None,
                           version: Option[String] = None,
                           locktime: Long = 0,
                           time: Option[Long] = None,
                           first_seen: Option[Long] = None,
                           propagation: Option[String] = None,
                           double_spend: Option[Boolean] = None,
                           size: Option[Int] = None,
                           vsize: Option[Int] = None,
                           input_amount: Option[String] = None,
                           input_amount_int: Option[Long] = None,
                           output_amount: Option[String] = None,
                           output_amount_int: Option[Long] = None,
                           fee: Option[String] = None,
                           fee_int: Option[Long] = None,
                           fee_size: Option[String] = None,
                           coinbase: Option[Boolean] = None,
                           input_count: Option[Int] = None,
                           inputs: Option[List[InOrOutDetails]] = None,
                           output_count: Option[Int] = None,
                           outputs: Option[List[InOrOutDetails]] = None,
                           tx_index: Option[Long] = None,
                           block_index: Option[Long] = None
                          )

    case class BlockDetails(height: Long,
                            confirmations: Int,
                            hash: String,
                            previous_block_hash: String,
                            next_block_hash: Option[String] = None,
                            merkleroot: String,
                            chainwork: String,
                            size: Long,
                            stripped_size: Long,
                            version: Int,
                            time: Long,
                            first_seen: Long,
                            nonce: Long,
                            bits: String,
                            difficulty: String,
                            input_count: Long,
                            output_count: Long,
                            input_amount: String,
                            input_amount_int: Long,
                            output_amount: String,
                            output_amount_int: Long,
                            fees: String,
                            fees_int: Long,
                            fee_size: String,
                            reward: String,
                            reward_int: Long,
                            pool: Option[Pool] = None,
                            coinbase: String,
                            dupe_coinbase: Option[String] = None,
                            transaction_count: Int,
                            transaction_paging: Option[Paging] = None,
                            transactions: Option[List[Transaction]] = None
                           )

    sealed trait SmartBitResponse

    case class RecentBlocksResponse(success: Boolean,
                                    paging: Paging = emptyPaging,
                                    blocks: Option[List[BlockDetails]] = None) extends SmartBitResponse

    case class DetailedBlockResponse(success: Boolean,
                                     block: BlockDetails) extends SmartBitResponse

  }

}
