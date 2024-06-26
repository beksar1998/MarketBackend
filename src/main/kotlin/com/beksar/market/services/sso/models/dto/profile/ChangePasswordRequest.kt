package com.beksar.market.services.sso.models.dto.profile

class ChangePasswordRequest(
    val oldPassword: String? = null,
    val confirmNewPassword: String? = null,
    val newPassword: String? = null,
)