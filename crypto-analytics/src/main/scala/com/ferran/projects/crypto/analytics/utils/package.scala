package com.ferran.projects.crypto.analytics

import java.time.format._

import com.ferran.projects.crypto.analytics.model.CryptoAnalyticModel.SmartBit._
import io.circe._
import io.circe.generic.semiauto._
import io.circe.syntax._

package object utils {

  implicit final val decoder52 = deriveDecoder[Sign]
  implicit final val decoder51 = deriveDecoder[AddressNum]

  implicit final val decoder42 = deriveDecoder[OutputDetails]
  implicit final val decoder41 = deriveDecoder[InputDetails]

  implicit final val decoder33 = deriveDecoder[Pool]
  implicit final val decoder32 = deriveDecoder[Transaction]
  implicit final val decoder31 = deriveDecoder[Paging]

  implicit final val decoder2 = deriveDecoder[BlockDetails]

  implicit final val decoder12 = deriveDecoder[DetailedBlockResponse]
  implicit final val decoder11 = deriveDecoder[RecentBlocksResponse]



  implicit final val encoder52 = deriveEncoder[Sign]
  implicit final val encoder51 = deriveEncoder[AddressNum]

  implicit final val encoder42 = deriveEncoder[OutputDetails]
  implicit final val encoder41 = deriveEncoder[InputDetails]

  implicit final val encoder33 = deriveEncoder[Pool]
  implicit final val encoder32 = deriveEncoder[Transaction]
  implicit final val encoder31 = deriveEncoder[Paging]

  implicit final val encoder2 = deriveEncoder[BlockDetails]

  implicit final val encoder12 = deriveEncoder[DetailedBlockResponse]
  implicit final val encoder11 = deriveEncoder[RecentBlocksResponse]


}
