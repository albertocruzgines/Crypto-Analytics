package com.ferran.projects.crypto.analytics.model

import com.ferran.projects.crypto.analytics.model.CryptoAnalyticModel.SmartBit._

object TestData {

  val paging =
    Paging( List.empty,
      0,
      "",
      "",
      "",
      "",
      "",
      "" )

  val pool = Pool("", "")

  val addressA = AddressNum("AAAAA")
  val addressE = AddressNum("EEEEE")
  val addressC = AddressNum("CCCCC")

  val sign = Sign("", "")

  val input = InOrOutDetails( List(addressA),
    "",
    0,
    "",
    0,
    sign,
    sign,
    0,
    "",
    List.empty,
    0,
    ""
  )

  val output = InOrOutDetails(List(addressE),
    "",
    0,
    "",
    0,
    sign,
    sign,
    0,
    "",
    List.empty,
    0,
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
    "",
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
    "",
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
    "",
    0,
    paging,
    List(tx1)
  )


  val recentBlocks = RecentBlocksResponse(true, paging, List(block))

  val detailedBlock = DetailedBlockResponse(true, block)

}
