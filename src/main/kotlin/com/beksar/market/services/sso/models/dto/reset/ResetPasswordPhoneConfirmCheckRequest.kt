package com.beksar.market.services.sso.models.dto.reset

class ResetPasswordPhoneConfirmCheckRequest(
    val phone: String? = null,
    val code: Int? = null,
)