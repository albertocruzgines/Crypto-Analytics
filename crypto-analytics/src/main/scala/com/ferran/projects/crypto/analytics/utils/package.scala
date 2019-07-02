package com.ferran.projects.crypto.analytics

import java.time.format._

import com.ferran.projects.crypto.analytics.model.CryptoAnalyticModel.Shapeshift.ShapeshiftResponse
import com.ferran.projects.crypto.analytics.model.CryptoAnalyticModel.SmartBit._
import io.circe._
import io.circe.generic.semiauto._
import io.circe.syntax._

package object utils {

  implicit final val decoder52 = deriveDecoder[Sign]
//  implicit final val decoder51 = deriveDecoder[Address]

  implicit final val decoder42 = deriveDecoder[InOrOutDetails]

  implicit final val decoder33 = deriveDecoder[Pool]
  implicit final val decoder32 = deriveDecoder[Transaction]

  implicit final val decoder31 = deriveDecoder[Paging]

  implicit final val decoder2 = deriveDecoder[BlockDetails]

  implicit final val decoder13 = deriveDecoder[ShapeshiftResponse]
  implicit final val decoder12 = deriveDecoder[DetailedBlockResponse]
  implicit final val decoder11 = deriveDecoder[RecentBlocksResponse]



  implicit final val encoder52 = deriveEncoder[Sign]
//  implicit final val encoder51 = deriveEncoder[Address]

  implicit final val encoder42 = deriveEncoder[InOrOutDetails]

  implicit final val encoder33 = deriveEncoder[Pool]
  implicit final val encoder32 = deriveEncoder[Transaction]
  implicit final val encoder31 = deriveEncoder[Paging]

  implicit final val encoder2 = deriveEncoder[BlockDetails]

  implicit final val encoder13 = deriveEncoder[ShapeshiftResponse]
  implicit final val encoder12 = deriveEncoder[DetailedBlockResponse]
  implicit final val encoder11 = deriveEncoder[RecentBlocksResponse]



}
