package com.beksar.market.services.sso.controllers


import com.beksar.market.core.extentions.response
import com.beksar.market.core.models.base.BaseResponse
import com.beksar.market.services.sso.core.annotations.Authentication
import com.beksar.market.services.sso.core.annotations.Roles
import com.beksar.market.services.sso.core.jwt.JWTService
import com.beksar.market.services.sso.core.roles.Role
import com.beksar.market.services.sso.models.dto.refresh.RefreshTokenRequest
import com.beksar.market.services.sso.models.dto.refresh.RefreshTokenResponse
import com.beksar.market.services.sso.services.RefreshTokenService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api/v1/user/refresh-token")
class RefreshTokenController(private val service: RefreshTokenService, private val jwtService: JWTService) {


    @PostMapping
    @Authentication
    fun refreshToken(@RequestBody request: RefreshTokenRequest): BaseResponse<RefreshTokenResponse> {
        return service.updateToken(jwtService.userId, request.refreshToken.orEmpty()).response()
    }
}