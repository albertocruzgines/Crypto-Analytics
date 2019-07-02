package com.ferran.projects.crypto.analytics.model

import com.ferran.projects.crypto.analytics.model.CryptoAnalyticModel.SmartBit._
import io.circe.generic.JsonCodec
import io.circe.syntax._
import com.ferran.projects.crypto.analytics.utils.encoder12
import org.scalatest.{FlatSpec, MustMatchers}

class CryptoAnalyticalModelTest extends FlatSpec with MustMatchers{

  val paging =
    Paging( List.empty,
            "",
            "",
            "",
            None,
            "",
            None,
            None )

  val pool = Pool("", "")

  val addressA = AddressNum("AAAAA")
  val addressE = AddressNum("EEEEE")
  val addressC = AddressNum("CCCCC")

  val sign = Sign("", "")

  val input = InputDetails( List(addressA),
                            "",
                            0,
                            "",
                            0,
                            sign,
                            "",
                            List.empty,
                            0
  )

  val output = OutputDetails(List(addressE),
                             "",
                              0,
                              0,
                              sign,
                              0,
                              "",
                              ""
  )


  val tx1 = Transaction("",
                        "",
                        0,
                        0,
                        "",
                        0,
                        0,
                        0,
                        None,
                        false,
                        0,
                        0,
                        "",
                        0,
                        "",
                        0,
                        "",
                        0,
                        "",
                        false,
                        0,
                        List.empty,
                        0,
                        List(output),
                        0,
                        0
  )

  val block = BlockDetails( 0,
                            0,
                            "",
                            "",
                            None,
                            "",
                            "",
                            0,
                            0,
                            0,
                            0,
                            0,
                            0,
                            "",
                            "",
                            0,
                            0,
                            "",
                            0,
                            "",
                            0,
                            "",
                            0,
                            "",
                            "",
                            0,
                            pool,
                            "",
                            None,
                            0,
                            None,
                            Option(List(tx1))
                         )

  val recentBlocks = RecentBlocksResponse(true, Option(paging), List(block))

  val detailedBlock = DetailedBlockResponse(true, block)

  detailedBlock.asJson(encoder12)
}



