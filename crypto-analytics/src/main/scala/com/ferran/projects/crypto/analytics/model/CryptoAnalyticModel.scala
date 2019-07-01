package com.ferran.projects.crypto.analytics.model

object CryptoAnalyticModel {

  case class AccessToken(createdAt: String,
                          updatedAt: String,
                          id: String,
                          clientId: String,
                          expiresAt: String,
                          token: String,
                          userId: Option[String],
                          deletedAt: Option[String])

   case class AuthResponse(access_token: AccessToken, token_type: String)



}
