package com.beksar.market.services.sso.mapper

import com.beksar.market.services.sso.core.roles.Role
import com.beksar.market.services.sso.models.dto.user.UserFullResponse
import com.beksar.market.services.sso.models.entity.UserEntity

fun UserEntity.toUserFullResponse(roles: List<Role>,phones : List<String>): UserFullResponse {
    return UserFullResponse(
        id = this.id.orEmpty(),
        name = this.name.orEmpty(),
        surname = this.surname.orEmpty(),
        email = this.email.orEmpty(),
        phone = this.phone.orEmpty(),
        isEmilConfirmed = this.isEmailConfirmed,
        isPhoneConfirmed = this.isPhoneConfirmed,
        roles = roles,
        status = this.status,
        phones = phones
    )
}