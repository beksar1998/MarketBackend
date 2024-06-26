package com.beksar.market.services.sso.services.impl

import com.beksar.market.core.extentions.checkNotNull
import com.beksar.market.core.extentions.httpError
import com.beksar.market.core.extentions.saveWithException
import com.beksar.market.properties.JWTProperties
import com.beksar.market.services.sso.core.jwt.JWTService
import com.beksar.market.services.sso.errors.AuthErrors
import com.beksar.market.services.sso.models.dto.refresh.RefreshTokenResponse
import com.beksar.market.services.sso.models.entity.RefreshTokenEntity
import com.beksar.market.services.sso.repositories.RefreshTokenRepository
import com.beksar.market.services.sso.repositories.UserRepository
import com.beksar.market.services.sso.services.RefreshTokenService
import com.beksar.market.services.sso.services.RolesService
import org.springframework.stereotype.Service
import java.util.*


@Service
class RefreshTokenServiceImpl(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtProperties: JWTProperties,
    private val jwtService: JWTService,
    private val userRepository: UserRepository,
    private val rolesService: RolesService
) : RefreshTokenService {

    override fun createRefreshToken(userId: String): String {
        refreshTokenRepository.deleteAllByUserId(userId)
        val refreshToken = UUID.randomUUID().toString()
        val entity = RefreshTokenEntity(
            refreshToken = refreshToken,
            userId = userId,
            expiry = Date(Date().time + jwtProperties.refreshTime)
        )
        refreshTokenRepository.saveWithException(entity)
        return refreshToken
    }

    override fun updateToken(userId: String, refreshToken: String): RefreshTokenResponse {
        val refreshUser =
            refreshTokenRepository
                .findByUserIdAndRefreshToken(userId, refreshToken)
                .checkNotNull(AuthErrors.RefreshUserNotFound)

        if ((refreshUser.expiry ?: Date()) < Date()) httpError(AuthErrors.RefreshTokenTimeExpired)
        refreshTokenRepository.deleteAllByUserId(userId)
        val user = userRepository.findById(userId).checkNotNull(AuthErrors.UserNotFound)
        val roles = rolesService.userRoles(userId)
        val token = jwtService.generate(
            id = user.id.orEmpty(),
            email = user.email.orEmpty(),
            phone = user.phone.orEmpty(),
            roles = roles
        )
        val newRefreshToken = createRefreshToken(userId)
        return RefreshTokenResponse(
            token = token,
            refreshToken = newRefreshToken
        )
    }


}