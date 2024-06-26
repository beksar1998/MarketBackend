package com.beksar.market.services.sso.models.dto.register

class RegisterEmailConfirmRequest(
    val email: String? = null,
    val confirmCode: Int? = null,
)