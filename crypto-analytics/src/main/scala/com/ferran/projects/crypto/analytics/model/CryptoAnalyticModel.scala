package com.ferran.projects.crypto.analytics.model

object CryptoAnalyticModel {

  object Authorization {

    case class AccessToken(createdAt: String,
                           updatedAt: String,
                           id: String,
                           clientId: String,
                           expiresAt: String,
                           token: String,
                           userId: Option[String],
                           deletedAt: Option[String])

    case class AuthResponse(access_token: AccessToken,
                            token_type: String)
  }

  object SmartBit {

    sealed trait SmartBitRequest

    case class Paging(valid_sort: List[String],
                      limit: String,
                      sort: String,
                      dir: String,
                      prev: Option[String],
                      next: String,
                      prev_link: Option[String],
                      next_link: Option[String])

    case class Pool(name: String,
                    link: String)

    case class AddressNum(address: String)

    case class PubKey(asm: String,
                      hex: String)

    case class ScriptSig(asm: String,
                         hex: String)


    case class InputDetails(addresses: List[AddressNum],
                            value: String,
                            value_int: Int,
                            txid: String,
                            vout: Int,
                            script_sig: ScriptSig,
                            `type`: String,
                            witness: List[Option[String]],
                            sequence: Long
                           )

    case class OutputDetails(addresses: List[AddressNum],
                             value: Double,
                             value_int: Int,
                             n: Int,
                             script_pub_key: PubKey,
                             req_sigs: Int,
                             `type`: String,
                             spend_txid: String)

    case class Transaction(txid: String,
                           hash: String,
                           block: Long,
                           confirmations: Long,
                           version: String,
                           locktime: Int,
                           time: Long,
                           first_seen: Long,
                           propagation: Option[Any],
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
                           inputs: List[InputDetails],
                           output_count: Int,
                           outputs: List[OutputDetails],
                           tx_index: Long,
                           block_index: Long
                          )

    case class BlockDetails(height: Long,
                            confirmations: Long,
                            hash: String,
                            previous_block_hash: String,
                            next_block_hash: Option[Any],
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
                            dupe_coinbase: Option[Any],
                            transaction_count: Int,
                            transaction_paging: Option[Paging] = None,
                            transactions: Option[List[Transaction]] = None
                           )

    case class RecentBlocksRequest(success: Boolean,
                                   paging: Option[Paging],
                                   blocks: List[BlockDetails]) extends SmartBitRequest

    case class DetailedBlockRequest(success: Boolean,
                                    block: BlockDetails) extends SmartBitRequest
  }
}
