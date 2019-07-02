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
                                  withdraw: String,
                                  incomingCoin: Double,
                                  incomingType: String,
                                  transaction: String,
                                  transactionURL: String)
  }

  object SmartBit {

    sealed trait SmartBitResponse

    case class Paging(valid_sort: List[String],
                      limit: String,
                      sort: String,
                      dir: String,
                      prev: String = "",
                      next: String,
                      prev_link: String = "",
                      next_link: String = "")

    def emptyPaging = Paging(List.empty,"" ,"" ,"" ,"" ,"", "")

    case class Pool(name: String,
                    link: String)

    case class AddressNum(address: String)

    case class Sign(asm: String,
                    hex: String)

    def emptySign = Sign("", "")


    case class InOrOutDetails(addresses: List[AddressNum],
                              value: String,
                              value_int: Int,
                              txid: String = "",
                              vout: Int = 0,
                              script_sig: Sign = emptySign,
                              script_pub_key: Sign = emptySign,
                              req_sigs: Int = 0,
                              `type`: String,
                              witness: List[String] = List.empty,
                              sequence: Long = 0,
                              spend_txid: String = ""
                             )

    case class Transaction(txid: String,
                           hash: String,
                           block: Long,
                           confirmations: Long,
                           version: String,
                           locktime: Int,
                           time: Long,
                           first_seen: Long,
                           propagation: String = "",
                           double_spend: Boolean,
                           size: Int,
                           vsize: Int,
                           input_amount: String,
                           input_amount_int: Int,
                           output_amount: String,
                           output_amount_int: Int,
                           fee: String,
                           fee_int: Int,
                           fee_size: String,
                           coinbase: Boolean,
                           input_count: Int,
                           inputs: List[InOrOutDetails],
                           output_count: Int,
                           outputs: List[InOrOutDetails],
                           tx_index: Long,
                           block_index: Long
                          )

    case class BlockDetails(height: Long,
                            confirmations: Long,
                            hash: String,
                            previous_block_hash: String,
                            next_block_hash: String = "",
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
                            input_amount_int: Int,
                            output_amount: String,
                            output_amount_int: Int,
                            fees: String,
                            fees_int: Int,
                            fee_size: String,
                            reward: String,
                            reward_int: Int,
                            pool: Pool,
                            coinbase: String,
                            dupe_coinbase: String = "",
                            transaction_count: Int,
                            transaction_paging: Paging = emptyPaging,
                            transactions: List[Transaction] = List.empty
                           )

    case class RecentBlocksResponse(success: Boolean,
                                    paging: Paging = emptyPaging,
                                    blocks: List[BlockDetails]) extends SmartBitResponse

    case class DetailedBlockResponse(success: Boolean,
                                     block: BlockDetails) extends SmartBitResponse

  }

}
