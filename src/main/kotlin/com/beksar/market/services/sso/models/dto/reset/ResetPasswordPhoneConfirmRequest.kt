package com.beksar.market.services.sso.models.dto.reset

class ResetPasswordPhoneConfirmRequest(
    val phone: String? = null,
    val code: Int? = null,
    val password: String? = null,
    val confirmNewPassword: String? = null
)