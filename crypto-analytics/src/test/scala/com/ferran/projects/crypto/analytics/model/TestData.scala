package com.ferran.projects.crypto.analytics.model

import com.ferran.projects.crypto.analytics.model.CryptoAnalyticModel.SmartBit._

object TestData {

  val paging =
    Paging( List.empty,
      0,
      "",
      "",
      None,
      "",
      None,
      "" )

  val pool = Pool(None, None)


  val sign = Sign("", "")

  val input = InOrOutDetails( Some(List("AAAA")),
    None,
    0,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None
  )

  val output = InOrOutDetails( Some(List("EEEEE")),
    None,
    0,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None,
    None
  )

  val tx1 = Transaction(outputs = Some(List(output)))

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
    None,
    "",
    None,
    0,
    None,
    None
  )


  val recentBlocks = RecentBlocksResponse(true, paging, Some(List(block)))

  val detailedBlock = DetailedBlockResponse(true, block)

}
