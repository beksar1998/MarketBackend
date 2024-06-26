package com.beksar.market.services.sso.services

import com.beksar.market.services.sso.models.dto.register.RegisterEmailConfirmRequest
import com.beksar.market.services.sso.models.dto.register.RegisterEmailRequest
import com.beksar.market.services.sso.models.dto.register.RegisterPhoneConfirmRequest
import com.beksar.market.services.sso.models.dto.register.RegisterPhoneRequest

interface RegisterService {
    fun registerEmail(request: RegisterEmailRequest): Boolean
    fun confirmEmail(request: RegisterEmailConfirmRequest): Boolean
    fun registerPhone(request: RegisterPhoneRequest): Boolean
    fun confirmPhone(request: RegisterPhoneConfirmRequest): Boolean

}