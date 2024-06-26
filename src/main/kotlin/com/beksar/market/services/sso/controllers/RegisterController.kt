package com.beksar.market.services.sso.controllers


import com.beksar.market.core.extentions.response
import com.beksar.market.core.models.base.BaseResponse
import com.beksar.market.services.sso.models.dto.register.RegisterEmailConfirmRequest
import com.beksar.market.services.sso.models.dto.register.RegisterEmailRequest
import com.beksar.market.services.sso.models.dto.register.RegisterPhoneConfirmRequest
import com.beksar.market.services.sso.models.dto.register.RegisterPhoneRequest
import com.beksar.market.services.sso.services.RegisterService
import com.beksar.market.services.sso.validators.validate
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/register")
class RegisterController(private val service: RegisterService) {


    @PostMapping("email")
    fun registerEmail(@RequestBody request: RegisterEmailRequest): BaseResponse<Boolean> {
        request.validate()
        return service.registerEmail(request).response()
    }

    @PutMapping("email/confirm")
    fun confirmEmail(@RequestBody request: RegisterEmailConfirmRequest): BaseResponse<Boolean> {
        request.validate()
        return service.confirmEmail(request).response()
    }

    @PostMapping("phone")
    fun registerPhone(@RequestBody request: RegisterPhoneRequest): BaseResponse<Boolean> {
        request.validate()
        return service.registerPhone(request).response()
    }

    @PutMapping("phone/confirm")
    fun confirmPhone(@RequestBody request: RegisterPhoneConfirmRequest): BaseResponse<Boolean> {
        request.validate()
        return service.confirmPhone(request).response()
    }


}