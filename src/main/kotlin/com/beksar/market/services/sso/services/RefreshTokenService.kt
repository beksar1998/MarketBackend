package com.beksar.market.services.sso.services

import com.beksar.market.services.sso.models.dto.refresh.RefreshTokenResponse

interface RefreshTokenService {
    fun createRefreshToken(userId: String): String
    fun updateToken(userId: String, refreshToken: String): RefreshTokenResponse
}