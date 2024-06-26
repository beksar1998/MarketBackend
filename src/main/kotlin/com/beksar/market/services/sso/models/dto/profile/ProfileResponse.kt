package com.beksar.market.services.sso.models.dto.profile

import com.beksar.market.services.sso.core.roles.Role
import com.beksar.market.services.sso.core.user.UserStatus


class ProfileResponse(
    val name: String,
    val surname: String,
    val phone: String,
    val email: String,
    val photo: String,
    val roles: List<Role>,
    val status: UserStatus,
    val phones: List<String>
)