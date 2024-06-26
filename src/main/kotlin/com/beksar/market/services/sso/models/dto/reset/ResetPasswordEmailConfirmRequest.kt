package com.beksar.market.services.sso.models.dto.reset

class ResetPasswordEmailConfirmRequest(
    val email: String? = null,
    val confirmCode: Int? = null,
    val password: String? = null,
    val confirmNewPassword: String? = null
)