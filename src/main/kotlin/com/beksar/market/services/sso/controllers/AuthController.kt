package com.beksar.market.services.sso.controllers



import com.beksar.market.core.extentions.response
import com.beksar.market.core.models.base.BaseResponse
import com.beksar.market.services.models.dto.auth.AuthSmsRequest
import com.beksar.market.services.sso.models.dto.auth.AuthEmailRequest
import com.beksar.market.services.sso.models.dto.auth.AuthPhoneRequest
import com.beksar.market.services.sso.models.dto.auth.AuthSmsConfirmRequest
import com.beksar.market.services.sso.models.dto.profile.AuthProfileResponse
import com.beksar.market.services.sso.services.AuthService
import com.beksar.market.services.sso.validators.validate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/auth")
class AuthController(private val service: AuthService) {

    @PostMapping("email")
    fun authEmail(@RequestBody request: AuthEmailRequest): BaseResponse<AuthProfileResponse> {
        request.validate()
        return service.authEmail(request).response()
    }


    @PostMapping("phone")
    fun authPhone(@RequestBody request: AuthPhoneRequest): BaseResponse<AuthProfileResponse> {
        request.validate()
        return service.authPhone(request).response()
    }

    @PostMapping("phone/sms")
    fun authPhoneSms(@RequestBody request: AuthSmsRequest): BaseResponse<Boolean> {
        request.validate()
        return service.authPhoneSms(request).response()
    }

    @PostMapping("phone/sms/confirm")
    fun authPhoneSmsConfirm(@RequestBody request: AuthSmsConfirmRequest): BaseResponse<AuthProfileResponse> {
        request.validate()
        return service.authPhoneSmsConfirm(request).response()
    }

}

