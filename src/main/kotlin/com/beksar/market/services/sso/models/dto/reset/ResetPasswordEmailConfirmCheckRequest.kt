package com.beksar.market.services.sso.models.dto.reset

class ResetPasswordEmailConfirmCheckRequest(
    val email: String? = null,
    val code: Int? = null,
)