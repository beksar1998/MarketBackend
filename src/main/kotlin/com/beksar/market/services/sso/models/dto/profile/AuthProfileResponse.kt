package com.beksar.market.services.sso.models.dto.profile

import com.beksar.market.services.sso.core.roles.Role
import com.beksar.market.services.sso.core.user.UserStatus


class AuthProfileResponse(
    val id: String,
    val name: String,
    val surname: String,
    val phone: String,
    val email: String,
    val token: String,
    val refreshToken: String,
    val roles : List<Role>,
    val status: UserStatus,
    val phones : List<String>,

)