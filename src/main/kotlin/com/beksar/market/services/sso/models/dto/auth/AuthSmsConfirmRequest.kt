package com.beksar.market.services.sso.models.dto.auth

class AuthSmsConfirmRequest(
    val phone: String? = null,
    val code: Int? = null,
)