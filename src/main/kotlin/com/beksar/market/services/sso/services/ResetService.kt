package com.beksar.market.services.sso.services

import com.beksar.market.services.sso.models.dto.reset.*

interface ResetService {
    fun resetPasswordEmail(request: ResetPasswordEmailRequest): Boolean
    fun resetPasswordEmailConfirm(request: ResetPasswordEmailConfirmRequest): Boolean
    fun resetPasswordPhone(request: ResetPasswordPhoneRequest): Boolean
    fun resetPasswordPhoneConfirm(request: ResetPasswordPhoneConfirmRequest): Boolean
    fun resetPasswordPhoneConfirmCheck(request: ResetPasswordPhoneConfirmCheckRequest): Boolean
}