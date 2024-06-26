package com.beksar.market.services.sso.services

import com.beksar.market.services.models.dto.auth.AuthSmsRequest
import com.beksar.market.services.sso.models.dto.auth.AuthEmailRequest
import com.beksar.market.services.sso.models.dto.auth.AuthPhoneRequest
import com.beksar.market.services.sso.models.dto.auth.AuthSmsConfirmRequest
import com.beksar.market.services.sso.models.dto.profile.AuthProfileResponse

interface AuthService {
    fun authEmail(request: AuthEmailRequest): AuthProfileResponse
    fun authPhone(request: AuthPhoneRequest): AuthProfileResponse
    fun authPhoneSms(request: AuthSmsRequest): Boolean
    fun authPhoneSmsConfirm(request: AuthSmsConfirmRequest): AuthProfileResponse
}