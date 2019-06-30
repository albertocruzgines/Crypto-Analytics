package com.ferran.projects.crypto.analytics.model

object CryptoAnalyticModel {

  case class AuthResponse(createdAt: String,
                          updatedAt: String,
                          id: String,
                          clientId: String,
                          expiresAt: String,
                          token: String,
                          userId: Option[String],
                          deletedAt: Option[String])



}
