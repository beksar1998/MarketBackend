package com.beksar.market.services.sso.models.dto.user

import com.beksar.market.services.sso.core.roles.Role
import com.beksar.market.services.sso.core.user.UserStatus

class UserFullResponse(
    val id: String,
    val name: String,
    val surname: String,
    val email: String,
    val phone: String,
    val roles: List<Role>,
    val isPhoneConfirmed: Boolean,
    val isEmilConfirmed: Boolean,
    val status: UserStatus,
    val phones: List<String>
)