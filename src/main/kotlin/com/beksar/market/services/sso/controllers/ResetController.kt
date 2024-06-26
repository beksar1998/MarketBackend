package com.beksar.market.services.sso.controllers


import com.beksar.market.core.extentions.response
import com.beksar.market.core.models.base.BaseResponse
import com.beksar.market.services.sso.models.dto.reset.*
import com.beksar.market.services.sso.services.ResetService
import com.beksar.market.services.sso.validators.validate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/reset")
class ResetController(private val service: ResetService) {

    @PostMapping("email")
    fun resetPasswordEmail(@RequestBody request: ResetPasswordEmailRequest): BaseResponse<Boolean> {
        request.validate()
        return service.resetPasswordEmail(request).response()
    }

    @PostMapping("email/confirm")
    fun resetPasswordEmailConfirm(@RequestBody request: ResetPasswordEmailConfirmRequest): BaseResponse<Boolean> {
        request.validate()
        return service.resetPasswordEmailConfirm(request).response()
    }


    @PostMapping("phone")
    fun resetPasswordPhone(@RequestBody request: ResetPasswordPhoneRequest): BaseResponse<Boolean> {
        request.validate()
        return service.resetPasswordPhone(request).response()
    }

    @PostMapping("phone/confirm/check")
    fun resetPasswordPhone(@RequestBody request: ResetPasswordPhoneConfirmCheckRequest): BaseResponse<Boolean> {
        return service.resetPasswordPhoneConfirmCheck(request).response()
    }


    @PostMapping("phone/confirm")
    fun resetPasswordPhoneConfirm(@RequestBody request: ResetPasswordPhoneConfirmRequest): BaseResponse<Boolean> {
        request.validate()
        return service.resetPasswordPhoneConfirm(request).response()
    }


}