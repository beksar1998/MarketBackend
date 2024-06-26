package com.beksar.market.services.sso.controllers

import com.beksar.market.core.extentions.response
import com.beksar.market.core.models.base.BaseResponse
import com.beksar.market.services.sso.models.dto.firebase.AddFirebaseTokenRequest
import com.beksar.market.services.sso.services.ProfileService
import com.beksar.market.services.sso.core.annotations.Authentication
import com.beksar.market.services.sso.core.jwt.JWTService
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/profile/firebase")
class UserFirebaseController(private val service: ProfileService, private val jwtService: JWTService) {

    @PutMapping
    @Authentication
    fun addFirebaseToken(@RequestBody request: AddFirebaseTokenRequest): BaseResponse<Boolean> {
        val userId = jwtService.userId
        return service.addFirebaseToken(userId, request).response()
    }

}