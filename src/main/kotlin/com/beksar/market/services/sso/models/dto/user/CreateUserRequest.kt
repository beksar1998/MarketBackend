package com.beksar.market.services.sso.models.dto.user

import com.beksar.market.services.sso.core.roles.Role

class CreateUserRequest(
    val name :String? = null,
    val surname :String? = null,
    val phone: String? = null,
    val email: String? = null,
    val password: String? = null,
    val roles: List<Role>? = null,
)